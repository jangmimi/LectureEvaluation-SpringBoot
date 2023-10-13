package com.springproject.repository;

import com.springproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    void deleteByUserNumber(Long userNumber);

    Optional<User> findByUserNumber(Long userNumber);
}
