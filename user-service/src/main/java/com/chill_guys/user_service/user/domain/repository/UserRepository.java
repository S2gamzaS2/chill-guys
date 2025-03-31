package com.chill_guys.user_service.user.domain.repository;

import com.chill_guys.user_service.user.domain.entity.User;
import com.chill_guys.user_service.user.domain.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    Optional<User> findById(Long id);

    void save(User user);

    Optional<User> findByUsername(String username);

    Page<User> findByRole(UserRole roleEnum, Pageable pageable);

    Page<User> findByAll(Pageable pageable);
}
