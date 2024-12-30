package com.fmi.discord.services;

import com.fmi.discord.entities.Server;
import com.fmi.discord.mappers.ServerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {
    private final JdbcTemplate db;

    public ServerService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createServer(Server server, int userId) {
        String serverQuery = "INSERT INTO SERVERS (name) VALUES (?)";
        this.db.update(serverQuery, server.getName());

        String lastInsertedQuery = "SELECT * FROM SERVERS ORDER BY created_at DESC LIMIT 1";
        List<Server> lastServer = this.db.query(lastInsertedQuery, new ServerRowMapper());
        int lastServerId = lastServer.get(0).getId();

        String membershipQuery = "INSERT INTO MEMBERSHIPS (user_id, server_id, role_id) VALUES (?, ?, ?)";
        this.db.update(membershipQuery, userId, lastServerId, 2);

        return true;
    }

    public Server getServerById(int id) {
        String query = "SELECT * FROM SERVERS WHERE id = ?";
        List<Server> collection = this.db.query(query, new ServerRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean deleteServer(int serverId) {
        String query = "DELETE FROM SERVERS WHERE id = ?";
        this.db.update(query, serverId);

        return true;
    }

    public boolean updateServer(int serverId, Server server) {
        String query = "UPDATE SERVERS SET name = ? WHERE is_active = TRUE AND id = ?";
        this.db.update(query, server.getName(), serverId);

        return true;
    }
}
