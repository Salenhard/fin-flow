package DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
// TODO add validation
@Data
public class SignInRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
