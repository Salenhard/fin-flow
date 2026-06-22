package dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDto {
    @Min(1)
    @NotNull
    BigDecimal amount;
}
