package controller;

import DTO.ChangePassRequest;
import DTO.SignInRequest;
import DTO.SignUpRequest;
import entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.AuthService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/{username}")
    public ResponseEntity<Void> changePassword(@PathVariable String username, @RequestBody ChangePassRequest changePassRequest) {
        authService.changePassword(username, changePassRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public @ResponseBody ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        String string = authService.signIn(signInRequest);
        return ResponseEntity.ok(string);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        User user = authService.signUp(signUpRequest);
        return ResponseEntity.ok(user);
    }
}
