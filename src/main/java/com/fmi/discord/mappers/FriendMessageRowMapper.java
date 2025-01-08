package com.fmi.discord.mappers;

import com.fmi.discord.entities.FriendMessageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMessageRowMapper implements RowMapper<FriendMessageDTO> {
    @Override
    public FriendMessageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendMessageDTO friendMessageDto = new FriendMessageDTO();
        friendMessageDto.setId(rs.getInt("id"));
        friendMessageDto.setSenderId(rs.getInt("sender_id"));
        friendMessageDto.setReceiverId(rs.getInt("receiver_id"));
        friendMessageDto.setContent(rs.getString("content"));
        friendMessageDto.setSenderUsername(rs.getString("sender_username"));
        friendMessageDto.setCreatedAt(rs.getDate("created_at"));
        return friendMessageDto;
    }
}
