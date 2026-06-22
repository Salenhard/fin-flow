package dto;

import entity.Status;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.UUID;

public record PaymentDto(
        UUID id,
        UUID orderId,
        UUID userId,
        BigDecimal amount,
        Status status,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}
