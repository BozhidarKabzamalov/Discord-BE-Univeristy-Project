package com.fmi.discord.services;

import com.fmi.discord.entities.User;
import com.fmi.discord.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcTemplate db;

    public UserService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createUser(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT into USERS (username, password) VALUES (?, ?)");
        this.db.update(query.toString(), user.getUsername(), user.getPassword());
        return true;
    }

    public User getUserById(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM USERS WHERE id = ?");
        var collection = this.db.query(query.toString(), new UserRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }
}
