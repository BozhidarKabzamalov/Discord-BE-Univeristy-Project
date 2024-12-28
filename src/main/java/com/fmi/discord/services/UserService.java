package com.fmi.discord.services;

import com.fmi.discord.entities.User;
import com.fmi.discord.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final JdbcTemplate db;

    public UserService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createUser(User user) {
        String query = "INSERT into USERS (username, password) VALUES (?, ?)";
        this.db.update(query.toString(), user.getUsername(), user.getPassword());
        return true;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM USERS WHERE id = ?";
        var collection = this.db.query(query.toString(), new UserRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE username = ?";
        var users = this.db.query(query, new UserRowMapper(), username);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM USERS";
        var users = this.db.query(query, new UserRowMapper());

        if (users.isEmpty()) {
            return null;
        }

        return users;
    }
}
