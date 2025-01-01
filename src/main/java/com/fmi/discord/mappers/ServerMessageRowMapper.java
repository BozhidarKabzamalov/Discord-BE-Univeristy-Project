package com.fmi.discord.mappers;

import com.fmi.discord.entities.ServerMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerMessageRowMapper implements RowMapper<ServerMessage> {
    @Override
    public ServerMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setId(rs.getInt("id"));
        serverMessage.setSenderId(rs.getInt("sender_id"));
        serverMessage.setServerId(rs.getInt("server_id"));
        serverMessage.setContent(rs.getString("content"));
        return serverMessage;
    }
}
