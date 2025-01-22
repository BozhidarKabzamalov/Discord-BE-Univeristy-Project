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

    public Server createServer(Server server, int userId) {
        String serverQuery = "INSERT INTO SERVERS (name) VALUES (?)";
        this.db.update(serverQuery, server.getName());

        String lastInsertedQuery = "SELECT * FROM SERVERS ORDER BY created_at DESC LIMIT 1";
        List<Server> lastServer = this.db.query(lastInsertedQuery, new ServerRowMapper());
        int lastServerId = lastServer.get(0).getId();

        String membershipQuery = "INSERT INTO MEMBERSHIPS (user_id, server_id, role_id) VALUES (?, ?, ?)";
        this.db.update(membershipQuery, userId, lastServerId, 2);

        if (lastServer.isEmpty()) {
            return null;
        }

        return lastServer.get(0);
    }

    public Server getServerById(int id) {
        String query = "SELECT * FROM SERVERS WHERE id = ? AND is_active = true";
        List<Server> collection = this.db.query(query, new ServerRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean deleteServer(int serverId) {
        String query = "UPDATE SERVERS SET is_active = false WHERE id = ?";
        this.db.update(query, serverId);

        return true;
    }

    public boolean updateServer(int serverId, Server server) {
        String query = "UPDATE SERVERS SET name = ? WHERE is_active = TRUE AND id = ?";
        this.db.update(query, server.getName(), serverId);

        return true;
    }

    public List<Server> getAllServersByUserId(int userId) {
        String query = "SELECT servers.id, servers.name, servers.is_active, servers.created_at FROM memberships JOIN servers ON memberships.server_id = servers.id WHERE memberships.user_id = ? AND servers.is_active = TRUE;";

        List<Server> collection = this.db.query(query, new ServerRowMapper(), userId);

        if (collection.isEmpty()) {
            return null;
        }

        return collection;
    }
}
