package dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCancelledDto(
        UUID eventId,
        UUID orderId,
        UUID userId,
        String reason,
        LocalDateTime cancelledAt
) {
}
