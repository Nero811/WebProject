package com.example.myspring.Service;

import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;

import java.util.List;

public interface UserService {
    User login(String email, String password);
    User getUserById(Integer userId);
    Integer register(UserRequest userRequest);
}
