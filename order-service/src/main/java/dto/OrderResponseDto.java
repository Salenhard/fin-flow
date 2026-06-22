package dto;

import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.UUID;

@Data
public class OrderResponseDto {
    UUID id;
    UUID userId;
    BigDecimal amount;
    String status;
    Timestamp createdAt;
    Timestamp orderDate;
}
