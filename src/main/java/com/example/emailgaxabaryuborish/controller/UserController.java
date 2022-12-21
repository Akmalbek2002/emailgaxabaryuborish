package com.example.emailgaxabaryuborish.controller;

import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.LoginDto;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/qoshish")
    public HttpEntity<?> userAdd(@RequestBody UserDto userDto){
        ApiResponse apiResponse=userService.UserAdd(userDto);
       return ResponseEntity.status(apiResponse.isHolat()? HttpStatus.OK:HttpStatus.ALREADY_REPORTED).body(apiResponse.getXabar());
    }
    @GetMapping("/tasdiqlash")
    public HttpEntity<?> userConfirm(@RequestParam String email,@RequestParam String emailcode){
        ApiResponse apiResponse=userService.userConfirm(email,emailcode);
        return ResponseEntity.status(apiResponse.isHolat()?200:409).body(apiResponse.getXabar());
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=userService.login(loginDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:409).body(apiResponse.getXabar());
    }
}
