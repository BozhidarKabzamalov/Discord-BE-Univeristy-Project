package com.fmi.discord.mappers;

import com.fmi.discord.entities.Server;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerRowMapper implements RowMapper<Server> {
    @Override
    public Server mapRow(ResultSet rs, int rowNum) throws SQLException {
        Server server = new Server();
        server.setId(rs.getInt("id"));
        server.setName(rs.getString("name"));
        server.setCreatedAt(rs.getDate("created_at"));
        return server;
    }
}
