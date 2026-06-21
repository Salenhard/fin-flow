package DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    @Min(3)
    @Schema(description = "Users username", example = "Username")
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @Schema(description = "Users password", example = "12345Ab!")
    private String password;
    @NotBlank
    @Email
    @Schema(description = "Users email", example = "email@gmail.com")
    private String email;
}
