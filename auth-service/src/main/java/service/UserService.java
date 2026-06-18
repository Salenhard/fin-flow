package service;

import entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    User findByUsername(String username);

    Page<User> findAll(Pageable pageable);

    void deleteByUsername(String username);

    User save(User user);

    User update(User user, UUID id);

    User findById(UUID id);
}
