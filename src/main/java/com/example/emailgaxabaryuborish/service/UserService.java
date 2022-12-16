package com.example.emailgaxabaryuborish.service;

import com.example.emailgaxabaryuborish.entity.Enum.Lavozimlar;
import com.example.emailgaxabaryuborish.entity.Users;
import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.repository.LavozimRepository;
import com.example.emailgaxabaryuborish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.hibernate.cfg.AvailableSettings.USER;

@Service
public class UserService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LavozimRepository lavozimRepository;
    public ApiResponse UserAdd(UserDto userDto) {
        boolean b = userRepository.existsByUsername(userDto.getUsername());
        if(b){
            return new ApiResponse("Bunday foydalanuvchi mavjud",false);
        }
        Users users=new Users();
        users.setIsm(userDto.getIsm());
        users.setFamiliya(userDto.getFamiliya());
        users.setTelNomer(userDto.getTelnomer());
        users.setUsername(userDto.getUsername());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setLavozimList(lavozimRepository.findByLavozimlar(Lavozimlar.USER));
        users.setEmailCode(UUID.randomUUID().toString().substring(0,6));
        userRepository.save(users);
        return new ApiResponse("Ro'yhatdan o'tdingiz!",true);
    }
}
