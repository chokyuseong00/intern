package com.intern.user.infrastructure.repository;

import com.intern.user.domain.model.User;
import com.intern.user.domain.repository.UserRepository;
import com.intern.user.global.exception.CustomException;
import com.intern.user.global.exception.ErrorCode;
import com.intern.user.infrastructure.persistence.JpaUserRepository;
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
}
