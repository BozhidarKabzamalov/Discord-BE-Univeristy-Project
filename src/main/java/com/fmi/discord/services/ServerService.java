package com.fmi.discord.services;

import com.fmi.discord.entities.Server;
import com.fmi.discord.mappers.ServerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    private final JdbcTemplate db;

    public ServerService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createServer(Server server) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO SERVERS (name, owner_id) VALUES (?, ?)");
        this.db.update(query.toString(), server.getName(), server.getOwnerId());
        return true;
    }

    public Server getServerById(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM SERVERS WHERE id = ?");
        var collection = this.db.query(query.toString(), new ServerRowMapper(), id);

        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean deleteServer(int serverId, int ownerId) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM SERVERS WHERE id = ? AND owner_id = ?");
        int rowsAffected = this.db.update(query.toString(), serverId, ownerId);
        return rowsAffected > 0; // Return true if the server was deleted
    }
}
