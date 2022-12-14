package com.example.emailgaxabaryuborish.controller;

import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/qoshish")
    public HttpEntity<?> userAdd(@RequestBody UserDto userDto){
        ApiResponse apiResponse=userService.UserAdd(userDto);
        return null;

    }
}
