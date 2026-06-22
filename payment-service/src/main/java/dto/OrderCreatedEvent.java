package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        UUID orderId,
        UUID userId,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt
) {}