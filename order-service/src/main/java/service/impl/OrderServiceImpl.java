package service.impl;

import dto.OrderCancelledDto;
import dto.OrderCreatedDto;
import dto.OrderRequestDto;
import dto.OrderResponseDto;
import entity.AuthUser;
import entity.Order;
import entity.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapstruct.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repository.OrderRepository;
import service.OrderService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    @Override
    public Page<OrderResponseDto> findByUserId(UUID userId, Pageable pageable, AuthUser authUser) {
        log.debug("Finding orders by userId {}, user={}", userId, authUser.username());
        if (!(authUser.isAdmin() || userId.equals(authUser.id()))) {
            log.warn("User {} does not have permission to orders by userId {}", authUser.username(), userId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        log.info("Found orders by userId {}, user={}", userId, authUser.username());
        return orders.map(orderMapper::entityToResponseDto);
    }

    @Override
    public OrderResponseDto findById(UUID id, AuthUser authUser) {
        log.debug("find order id={}, user={}", id, authUser.username());
        Order order = getById(id);
        OrderResponseDto responseDto = orderMapper.entityToResponseDto(order);
        log.info("found order id={}, user={}", id, authUser.username());
        return responseDto;
    }

    @Override
    public OrderResponseDto save(OrderRequestDto orderRequestDto, AuthUser authUser) {
        log.debug("save order requestDto={}, user={}", orderRequestDto, authUser.username());

        Order order = new Order();
        order.setUserId(authUser.id());
        order.setPrice(orderRequestDto.getPrice());
        order.setStatus(Status.Pending);
        order.setOrderDate(Timestamp.from(Instant.now()));
        order.setCreatedAt(Timestamp.from(Instant.now()));
        order = orderRepository.save(order);

        OrderCreatedDto orderCreatedDto = new OrderCreatedDto(
                UUID.randomUUID(),
                order.getId(),
                order.getUserId(),
                order.getPrice(),
                order.getStatus().name(),
                LocalDateTime.now()
        );

        orderEventProducer.sendOrderCreated(orderCreatedDto);

        log.info("save order responseDto={}", order);
        return orderMapper.entityToResponseDto(order);
    }

    @Override
    public OrderResponseDto update(OrderRequestDto orderRequestDto, UUID id, AuthUser authUser) {
        log.debug("update order requestDto={}, id={}", orderRequestDto, id);
        Order order = getById(id);

        if (!authUser.isAdmin()) {
            log.warn("User not allowed to update order");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN
                    , "You dont have permission to perform this operation");
        }
        order.setPrice(orderRequestDto.getPrice());
        OrderResponseDto responseDto = orderMapper.entityToResponseDto(orderRepository.save(order));
        log.info("update order responseDto={}", order);
        return responseDto;
    }

    @Override
    public void deleteById(UUID id, AuthUser authUser) {
        log.debug("delete order id={}, user={}", id, authUser.username());
        if (!authUser.isAdmin()) {
            log.warn("User not allowed to delete order");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have permission to perform this operation");
        }
        orderRepository.deleteById(id);
        log.info("delete order responseDto={}", id);
    }

    @Override
    public Page<OrderResponseDto> findAll(Pageable pageable, AuthUser authUser) {
        log.debug("findAll order pageable={}, user={}", pageable, authUser.username());
        Page<Order> orders = orderRepository.findAll(pageable);
        log.info("findAll order pageable={}, user={}", pageable, authUser.username());
        return orders.map(orderMapper::entityToResponseDto);
    }

    @Override
    public OrderResponseDto cancel(UUID id, AuthUser authUser) {
        log.debug("cancel order id={}, user={}", id, authUser.username());

        if (!(authUser.isAdmin() || id.equals(authUser.id()))) {
            log.warn("User not allowed to cancel order");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have permission to perform this operation");
        }

        Order order = getById(id);
        order.setStatus(Status.Cancelled);
        order = orderRepository.save(order);

        OrderCancelledDto orderCancelledDto = new OrderCancelledDto(
                UUID.randomUUID(),
                order.getId(),
                order.getUserId(),
                "USER_CANCELLED",
                LocalDateTime.now()
        );
        orderEventProducer.sendOrderCanceled(orderCancelledDto);

        OrderResponseDto responseDto = orderMapper.entityToResponseDto(order);
        log.info("cancel order responseDto={}", responseDto);
        return responseDto;
    }

    @Override
    public OrderResponseDto pay(UUID id, AuthUser authUser) {
        if (!(authUser.isAdmin() || id.equals(authUser.id()))) {
            log.warn("User not allowed to cancel order");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have permission to perform this operation");
        }
        Order order = getById(id);
        order.setStatus(Status.Completed);
        order = orderRepository.save(order);
        //TODO add remove money logic
        OrderResponseDto responseDto = orderMapper.entityToResponseDto(order);
        log.info("pay order id={}, user={}", id, authUser.username());
        return responseDto;
    }

    private Order getById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            log.warn("Order not found with id={}", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        });
    }
}
