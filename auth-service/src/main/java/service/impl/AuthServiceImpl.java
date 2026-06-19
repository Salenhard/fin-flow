package service.impl;

import DTO.ChangePassRequest;
import DTO.SignInRequest;
import DTO.SignUpRequest;
import entities.Role;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.AuthService;
import service.UserService;

import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Override
    public String signIn(SignInRequest signInRequest) {
        log.debug("sign in request: {}", signInRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());
        log.info("user successfully signed in: {}", userDetails.getUsername());
        return jwtService.generateToken(userDetails);
    }

    @Override
    public void changePassword(String username, ChangePassRequest changePassRequest) {
        log.debug("change password request: {}", username);
        User user = userService.findByUsername(username);
        user.setPasswordHash(encoder.encode(changePassRequest.getPassword()));
        userService.save(user);
        log.info("change password successful: {}", username);
    }

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        log.debug("sign up request: {}", signUpRequest.getUsername());
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPasswordHash(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(Collections.singletonList(Role.USER));
        user = userService.save(user);
        log.info("user successfully signed up: {}", user.getUsername());
        return user;
    }
}
