package com.intern.user.users.infrastructure.repository;

import com.intern.user.users.domain.model.User;
import com.intern.user.users.domain.repository.UserRepository;
import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import com.intern.user.users.infrastructure.persistence.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void save(User user) {
        jpaUserRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findById(Long userId) {
        return jpaUserRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
