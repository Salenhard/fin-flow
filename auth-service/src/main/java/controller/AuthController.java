package controller;

import DTO.ChangePassRequest;
import DTO.SignInRequest;
import DTO.SignUpRequest;
import entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.AuthService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/{username}")
    public ResponseEntity<Void> changePassword(@PathVariable String username, @RequestBody ChangePassRequest changePassRequest) {
        log.debug("UserService: Changing password for username={}", username);
        authService.changePassword(username, changePassRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public @ResponseBody ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        log.debug("UserService: Signing in with username={}", signInRequest.getUsername());
        String string = authService.signIn(signInRequest);
        return ResponseEntity.ok(string);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        log.debug("UserService: Signing up with username={}", signUpRequest.getUsername());
        User user = authService.signUp(signUpRequest);
        return ResponseEntity.ok(user);
    }
}
