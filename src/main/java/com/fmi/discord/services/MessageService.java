package com.fmi.discord.services;

import com.fmi.discord.entities.FriendMessage;
import com.fmi.discord.entities.FriendMessageDTO;
import com.fmi.discord.entities.ServerMessage;
import com.fmi.discord.entities.ServerMessageDTO;
import com.fmi.discord.mappers.FriendMessageRowMapper;
import com.fmi.discord.mappers.ServerMessageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final JdbcTemplate db;

    public MessageService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public ServerMessageDTO createServerMessage(ServerMessage serverMessage, int senderId) {
        String createServerMessageQuery = "INSERT INTO SERVER_MESSAGES (sender_id, server_id, content) VALUES (?, ?, ?)";
        this.db.update(createServerMessageQuery, senderId, serverMessage.getServerId(), serverMessage.getContent());

        String getLastCreatedServerMessageQuery = "SELECT sm.id AS message_id, sm.server_id, sm.sender_id, u.username AS sender_username, sm.content, sm.created_at FROM server_messages sm JOIN users u ON sm.sender_id = u.id WHERE sm.server_id = ? ORDER BY sm.created_at ASC";
        List<ServerMessageDTO> collection = this.db.query(getLastCreatedServerMessageQuery, new ServerMessageRowMapper(), serverMessage.getServerId());

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean createFiendMessage(FriendMessage friendMessage, int senderId) {
        String serverQuery = "INSERT INTO FRIEND_MESSAGES (sender_id, receiver_id, content) VALUES (?, ?, ?)";
        this.db.update(serverQuery, senderId, friendMessage.getReceiverId(), friendMessage.getContent());

        return true;
    }

    public List<ServerMessageDTO> getServerMessages(int serverId) {
        String serverQuery = "SELECT sm.id AS message_id, sm.server_id, sm.sender_id, u.username AS sender_username, sm.content, sm.created_at FROM server_messages sm JOIN users u ON sm.sender_id = u.id WHERE sm.server_id = ? ORDER BY sm.created_at ASC";
        List<ServerMessageDTO> collection = this.db.query(serverQuery, new ServerMessageRowMapper(), serverId);

        return collection;
    }

    public List<FriendMessageDTO> getFriendMessages(int friendId, int userId) {
        String serverQuery = "SELECT fm.*, u.username AS sender_username FROM friend_messages fm JOIN users u ON (u.id = fm.sender_id AND fm.sender_id = ?) OR (u.id = fm.receiver_id AND fm.receiver_id = ?) WHERE (fm.sender_id = ? AND fm.receiver_id = ?) OR (fm.sender_id = ? AND fm.receiver_id = ?) ORDER BY fm.created_at ASC;";
        List<FriendMessageDTO> collection = this.db.query(serverQuery, new FriendMessageRowMapper(), friendId, friendId , userId, friendId, friendId, userId);

        return collection;
    }
}
