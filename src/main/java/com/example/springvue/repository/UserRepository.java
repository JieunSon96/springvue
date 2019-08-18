package com.example.springvue.repository;

import com.example.springvue.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserNameOrEmail(String userName, String email);
    Optional<UserAccount> findByUsername(String username);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
}
