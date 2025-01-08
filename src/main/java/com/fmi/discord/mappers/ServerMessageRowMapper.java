package com.fmi.discord.mappers;

import com.fmi.discord.entities.ServerMessageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerMessageRowMapper implements RowMapper<ServerMessageDTO> {
    @Override
    public ServerMessageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServerMessageDTO serverMessageDTO = new ServerMessageDTO();
        serverMessageDTO.setId(rs.getInt("message_id"));
        serverMessageDTO.setSenderId(rs.getInt("sender_id"));
        serverMessageDTO.setServerId(rs.getInt("server_id"));
        serverMessageDTO.setSenderUsername(rs.getString("sender_username"));
        serverMessageDTO.setContent(rs.getString("content"));
        serverMessageDTO.setCreatedAt(rs.getDate("created_at"));
        return serverMessageDTO;
    }
}
