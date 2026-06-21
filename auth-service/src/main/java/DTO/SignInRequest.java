package DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @Schema(description = "Users username", example = "Username")
    @NotBlank
    private String username;
    @Schema(description = "Users password", example = "12345Ab!")
    @NotBlank
    private String password;
}
