package DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDto {
    @Schema(description = "Users uuid", example = "")
    private UUID uuid;
    @Schema(description = "Users username", example = "Username")
    private String username;
    @Schema(description = "Users email", example = "email@gmail.com")
    private String Email;
    @Schema(description = "Users roles", example = "User, Admin")
    private List<String> roles;
}
