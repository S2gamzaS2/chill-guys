package com.chill_guys.user_service.user.infrastructure.jpa;

import com.chill_guys.user_service.user.domain.entity.User;
import com.chill_guys.user_service.user.domain.entity.UserRole;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Page<User> findByRole(UserRole role, Pageable pageable);
}
