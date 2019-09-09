package com.example.springvue.repository;

import com.example.springvue.entity.RoleName;
import com.example.springvue.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(RoleName roleName);
}
