package com.example.myspring.Dao;

import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;

import java.util.List;

public interface UserDao {
    User login(String email, String password);
    User getUserById(Integer userId);
    String getUserByEmail(String userEmail);
    Integer createUser(UserRequest userRequest);
}
