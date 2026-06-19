package DTO;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID uuid;
    private String username;
    private String Email;
    private List<String> roles;
}
