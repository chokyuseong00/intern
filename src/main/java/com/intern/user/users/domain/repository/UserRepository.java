package com.intern.user.users.domain.repository;

import com.intern.user.users.domain.model.User;

public interface UserRepository {

    void save(User user);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    User findById(Long userId);
}
