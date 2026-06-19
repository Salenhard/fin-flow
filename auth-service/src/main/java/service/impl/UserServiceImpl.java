package service.impl;

import entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repository.UserRepository;
import service.UserService;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        log.debug("Searching user by username={}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username={} not found", username);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with username %s is not found".formatted(String.valueOf(username)));
        });
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Searching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteByUsername(String username) {
        log.debug("Deleting user by username={}", username);
        userRepository.deleteByUsername(username);
        log.info("User with username={} deleted", username);
    }

    @Override
    public User save(User user) {
        log.debug("Saving user={}", user);
        user = userRepository.save(user);
        log.info("User with username={} saved", user.getUsername());
        return user;
    }

    @Override
    public User update(User user, UUID id) {
        log.debug("Updating user with id={}", id);
        return userRepository.findById(id).map(user1 -> {
            user1.setUsername(user.getUsername());
            user1.setRoles(user.getRoles());
            user1 = userRepository.save(user1);
            log.info("User with username={} updated", user.getUsername());
            return user1;
        }).orElseThrow(() -> {
            log.warn("User with id={} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with id %s is not found!".formatted(String.valueOf(id)));
        });
    }

    @Override
    public User findById(UUID id) {
        log.debug("Finding user with id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with id %s is not found!".formatted(String.valueOf(id)));
                });
        log.info("User with username={} found", user.getUsername());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
