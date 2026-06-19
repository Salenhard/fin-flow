package DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    @NotEmpty
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotEmpty
    private List<String> roles;
}
