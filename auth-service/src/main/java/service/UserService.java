package service;

import DTO.UserRequestDto;
import DTO.UserResponseDto;
import entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserResponseDto findByUsername(String username);

    Page<User> findAll(Pageable pageable);

    void deleteByUsername(String username);

    UserResponseDto save(UserRequestDto user);

    UserResponseDto update(UserRequestDto user, UUID id);

    UserResponseDto findById(UUID id);

    void deleteById(UUID id);
}
