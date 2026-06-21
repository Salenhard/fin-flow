package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedDto(
        UUID eventId,
        UUID orderId,
        UUID userId,
        BigDecimal price,
        String status,
        LocalDateTime createdAt
) {}