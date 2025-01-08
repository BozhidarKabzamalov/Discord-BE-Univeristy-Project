package com.fmi.discord.services;

import com.fmi.discord.entities.Friend;
import com.fmi.discord.entities.Friendship;
import com.fmi.discord.mappers.FriendRowMapper;
import com.fmi.discord.mappers.FriendshipRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {
    private final JdbcTemplate db;

    public FriendshipService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean addFriend(int userId, int friendId) {
        String query = "INSERT INTO FRIENDSHIPS (user_id, friend_id) VALUES (?, ?);";
        this.db.update(query, userId, friendId);
        return true;
    }

    public boolean isUserAlreadyFriend(int userId, int friendId) {
        String query = "SELECT * FROM FRIENDSHIPS WHERE user_id = ? AND friend_id = ?;";
        List<Friendship> collection = this.db.query(query, new FriendshipRowMapper(), userId, friendId);

        return !collection.isEmpty();
    }

    public List<Friend> getFriendsOfUser(int userId) {
        String query = "SELECT u.id AS friend_id, u.username AS friend_username FROM users u JOIN friendships f ON u.id = f.friend_id WHERE f.user_id = ?";
        List<Friend> collection = this.db.query(query, new FriendRowMapper(), userId);

        return collection;
    }
}
