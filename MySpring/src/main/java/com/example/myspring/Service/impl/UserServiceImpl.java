package com.example.myspring.Service.impl;

import com.example.myspring.Dao.impl.UserDaoImpl;
import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;
import com.example.myspring.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User login(String email, String password) {

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        if (userDao.login(email, hashedPassword) != null) {
            return userDao.login(email, hashedPassword);
        } else {
            log.warn("帳號/密碼錯誤");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRequest userRequest) {

        // 檢查email是否被註冊
        if (userDao.getUserByEmail(userRequest.getEmail()) != null) {
            log.warn("此email: {} 已被註冊", userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRequest);
    }
}
