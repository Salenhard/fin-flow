package dto;

import entity.Status;

public record PaymentResult(
        Status status,
        String message
) {
}
