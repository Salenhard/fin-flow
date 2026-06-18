package controller;

import entities.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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

    @DeleteMapping("/{username}")
    public void deleteByUsername(@PathVariable String username) {
        log.debug("delete user by username: {}", username);
        userService.deleteByUsername(username);
    }

    @GetMapping
    public @ResponseBody PagedModel<User> findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        log.debug("find all users by page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<User> users = userService.findAll(pageable);
        return new PagedModel<>(users);
    }

    @GetMapping("/{id}")
    public @ResponseBody User findById(@PathVariable UUID id) {
        log.debug("find user by id: {}", id);
        return userService.findById(id);
    }

    @GetMapping("/{username}")
    public @ResponseBody User findByUsername(@PathVariable String username) {
        log.debug("find user by username: {}", username);
        return userService.findByUsername(username);
    }

    @PostMapping
    public @ResponseBody User save(@RequestBody User user) {
        log.debug("save user: {}", user);
        return userService.save(user);
    }

    @PutMapping
    public @ResponseBody User update(@RequestBody User user, @RequestBody UUID id) {
        log.debug("update user: {}", user);
        return userService.update(user, id);
    }
}
