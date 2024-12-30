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
        this.db.update(query, user.getUsername(), user.getPassword());
        return true;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM USERS WHERE id = ?";
        List<User> collection = this.db.query(query, new UserRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE username = ?";
        List<User> collection = this.db.query(query, new UserRowMapper(), username);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM USERS";
        List<User> collection = this.db.query(query, new UserRowMapper());

        if (collection.isEmpty()) {
            return null;
        }

        return collection;
    }
}
