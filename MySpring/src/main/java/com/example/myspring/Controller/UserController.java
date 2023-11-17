package com.example.myspring.Controller;

import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;
import com.example.myspring.Service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody UserRequest userRequest) {
        User user = userService.login(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest) {
        Integer userId = userService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(userId));
    }
}
