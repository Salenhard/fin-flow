package DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {
    @NotBlank
    @Schema(description = "Users username", example = "Username")
    private String username;
    @NotBlank
    @Email
    @Schema(description = "Users email", example = "email@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Users username", example = "12345Ab!")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;
    @NotEmpty
    @Schema(description = "Users roles", example = "User, Admin")
    private List<String> roles;
}
