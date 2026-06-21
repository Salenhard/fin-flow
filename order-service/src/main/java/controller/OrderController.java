package controller;

import dto.OrderRequestDto;
import dto.OrderResponseDto;
import entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<OrderResponseDto> update(@RequestBody OrderRequestDto order, @RequestBody UUID id, @RequestBody AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderService.update(order, id, authUser);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderRequestDto order, @RequestBody AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderService.save(order, authUser);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{id}/pay")
    public ResponseEntity<OrderResponseDto> pay(@PathVariable UUID id,
                                                @AuthenticationPrincipal AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderService.pay(id, authUser);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PagedModel<OrderResponseDto>> findByUserId(@PathVariable UUID userId,
                                                                     @ParameterObject Pageable pageable,
                                                                     @AuthenticationPrincipal AuthUser authUser) {
        Page<OrderResponseDto> orderResponseDtos = orderService.findByUserId(userId, pageable, authUser);
        PagedModel<OrderResponseDto> orderResponseDto = new PagedModel<>(orderResponseDtos);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable UUID id,
                                                     @AuthenticationPrincipal AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderService.findById(id, authUser);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<OrderResponseDto>> findAll(@ParameterObject Pageable pageable,
                                                                @AuthenticationPrincipal AuthUser authUser) {
        Page<OrderResponseDto> orderResponseDtos = orderService.findAll(pageable, authUser);
        PagedModel<OrderResponseDto> orderResponseDto = new PagedModel<>(orderResponseDtos);
        return ResponseEntity.ok(orderResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id,
                                           @AuthenticationPrincipal AuthUser authUser) {
        orderService.deleteById(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancel(@PathVariable UUID id,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderService.cancel(id, authUser);
        return ResponseEntity.ok(orderResponseDto);
    }
}
