package dto;

import entity.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public record PaymentEvent(
        UUID id,
        UUID orderId,
        UUID userId,
        BigDecimal amount,
        Status status,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}
