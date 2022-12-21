package com.example.emailgaxabaryuborish.repository;

import com.example.emailgaxabaryuborish.entity.Users;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(@Email String username);
    Optional<Users> findByUsernameAndEmailCode(String username, String emailcode);
    boolean existsByUsernameAndPassword(@Email String username, @Length(min = 8, max = 72) String password);
}
