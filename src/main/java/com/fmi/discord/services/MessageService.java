package com.fmi.discord.services;

import com.fmi.discord.entities.FriendMessage;
import com.fmi.discord.entities.ServerMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final JdbcTemplate db;

    public MessageService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createServerMessage(ServerMessage serverMessage, int senderId) {
        String serverQuery = "INSERT INTO SERVER_MESSAGES (sender_id, server_id, content) VALUES (?, ?, ?)";
        this.db.update(serverQuery, senderId, serverMessage.getServerId(), serverMessage.getContent());

        return true;
    }

    public boolean createFiendMessage(FriendMessage friendMessage, int senderId) {
        String serverQuery = "INSERT INTO FRIEND_MESSAGES (sender_id, receiver_id, content) VALUES (?, ?, ?)";
        this.db.update(serverQuery, senderId, friendMessage.getReceiverId(), friendMessage.getContent());

        return true;
    }
}
