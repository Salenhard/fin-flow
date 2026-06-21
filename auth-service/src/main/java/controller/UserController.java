package controller;

import DTO.UserRequestDto;
import DTO.UserResponseDto;
import entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Controller
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public @ResponseBody ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal User user) {
        log.debug("UserController: saving user={}", userRequestDto.getUsername());
        return ResponseEntity.ok(userService.save(userRequestDto, user));
    }

    @GetMapping
    public @ResponseBody PagedModel<User> findAll(@RequestParam Optional<Integer> page,
                                                  @RequestParam Optional<Integer> size) {
        log.debug("UserController: find all users by page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<User> users = userService.findAll(pageable);
        return new PagedModel<>(users);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        log.debug("UserController: find user by id: {}", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/{username}")
    public @ResponseBody ResponseEntity<UserResponseDto> findByUsername(@PathVariable String username) {
        log.debug("UserController: find user by username: {}", username);
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserRequestDto userRequestDto,
                                                                @PathVariable UUID id, @AuthenticationPrincipal User user) {
        log.debug("UserController: update user: {}", user);
        return ResponseEntity.ok(userService.update(userRequestDto, id, user));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        log.debug("UserController: delete user: {}", id);
        userService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}
