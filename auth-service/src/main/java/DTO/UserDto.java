package DTO;

import entities.Role;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String role;
}
