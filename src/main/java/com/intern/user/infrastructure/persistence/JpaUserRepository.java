package com.intern.user.infrastructure.persistence;

import com.intern.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
