package com.userLogin.repository.mapper;

import com.userLogin.model.Item;
import com.userLogin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import com.userLogin.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class UserMapper implements RowMapper<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));


        return user;
    }
}
