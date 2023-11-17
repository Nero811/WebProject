package com.example.myspring.Dao.impl;

import com.example.myspring.Dao.UserDao;
import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;
import com.example.myspring.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User login(String email, String password) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM user " +
                "WHERE email = :email AND password = :password";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM user " +
                "WHERE 1 = 1 ";
        sql += "AND user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> users = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String getUserByEmail(String userEmail) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date FROM user WHERE 1=1 ";

        sql += "AND email = :email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", userEmail);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0).getEmail();
        } else {
            return null;
        }
    }

    @Override
    public Integer createUser(UserRequest userRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date) " +
                "VALUES(:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRequest.getEmail());
        map.put("password", userRequest.getPassword());
        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }
}
