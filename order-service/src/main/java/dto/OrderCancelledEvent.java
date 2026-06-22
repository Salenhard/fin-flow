package dto;

import java.sql.Timestamp;
import java.util.UUID;

public record OrderCancelledEvent(
        UUID eventId,
        UUID orderId,
        UUID userId,
        String reason,
        Timestamp cancelledAt
) {
}
