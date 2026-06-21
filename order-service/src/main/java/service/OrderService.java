package service;

import com.sun.security.auth.UserPrincipal;
import dto.OrderRequestDto;
import dto.OrderResponseDto;
import entity.AuthUser;
import entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderResponseDto> findByUserId(UUID userId, Pageable pageable, AuthUser authUser);

    OrderResponseDto findById(UUID id, AuthUser authUser);

    OrderResponseDto save(OrderRequestDto order, AuthUser authUser);

    OrderResponseDto update(OrderRequestDto order, UUID id, AuthUser authUser);

    void deleteById(UUID id, AuthUser authUser);

    Page<OrderResponseDto> findAll(Pageable pageable, AuthUser authUser);

    OrderResponseDto cancel(UUID id, AuthUser authUser);

    OrderResponseDto pay(UUID id, AuthUser authUser);
}
