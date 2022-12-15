package com.example.emailgaxabaryuborish.service;

import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    public ApiResponse UserAdd(UserDto userDto) {
        boolean b = userRepository.existsByUsername(userDto.getUsername());
        if(b){
            return new ApiResponse("Bunday foydalanuvchi mavjud",false);
        }
    }
}
