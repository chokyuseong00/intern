package com.intern.user.infrastructure.persistence;

import com.intern.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

}
