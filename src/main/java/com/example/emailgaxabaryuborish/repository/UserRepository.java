package com.example.emailgaxabaryuborish.repository;

import com.example.emailgaxabaryuborish.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsernameAndEmailCode(String username, String emailcode);
}
