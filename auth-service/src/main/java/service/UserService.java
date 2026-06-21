package service;

import DTO.UserRequestDto;
import DTO.UserResponseDto;
import entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserResponseDto findByUsername(String username);

    Page<User> findAll(Pageable pageable);

    void deleteByUsername(String username);

    UserResponseDto save(UserRequestDto userRequestDto, User user);

    UserResponseDto update(UserRequestDto userRequestDto, UUID id, User user);

    UserResponseDto findById(UUID id);

    void deleteById(UUID id, User user);
}
