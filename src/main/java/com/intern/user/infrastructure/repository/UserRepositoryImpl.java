package com.intern.user.infrastructure.repository;

import com.intern.user.domain.repository.UserRepository;
import com.intern.user.infrastructure.persistence.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

}
