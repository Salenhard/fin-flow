package service.impl;

import DTO.UserRequestDto;
import DTO.UserResponseDto;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapstruct.UserMapper;
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

    private final UserMapper userMapper;

    @Override
    public UserResponseDto findByUsername(String username) {
        log.debug("UserService: Searching user by username={}", username);
        return userMapper.userToUserResponseDto(userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username={} not found", username);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with username %s is not found".formatted(String.valueOf(username)));
        }));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("UserService: Searching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteByUsername(String username) {
        log.debug("UserService: Deleting user by username={}", username);
        userRepository.deleteByUsername(username);
        log.info("User with username={} deleted", username);
    }

    @Override
    public UserResponseDto save(UserRequestDto userDto) {
        log.debug("UserService: Saving user={}", userDto);
        User user = userMapper.userRequestDtoToUser(userDto);
        user = userRepository.save(user);
        log.info("User with username={} saved", user.getUsername());
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto update(UserRequestDto userDto, UUID id) {
        User user = userMapper.userRequestDtoToUser(userDto);
        log.debug("UserService: Updating user with id={}", id);
        return userMapper.userToUserResponseDto(userRepository.findById(id).map(user1 -> {
            user1.setUsername(user.getUsername());
            user1.setRoles(user.getRoles());
            user1 = userRepository.save(user1);
            log.info("User with username={} updated", user.getUsername());
            return user1;
        }).orElseThrow(() -> {
            log.warn("User with id={} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with id %s is not found!".formatted(String.valueOf(id)));
        }));
    }

    @Override
    public UserResponseDto findById(UUID id) {
        log.debug("Finding user with id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with id %s is not found!".formatted(String.valueOf(id)));
                });
        log.info("User with username={} found", user.getUsername());
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("UserService: Deleting user with id={}", id);
        userRepository.deleteById(id);
        log.info("User with id={} deleted", id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Searching user by username={}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username={} not found", username);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User with username %s is not found".formatted(username));
        });
    }
}
