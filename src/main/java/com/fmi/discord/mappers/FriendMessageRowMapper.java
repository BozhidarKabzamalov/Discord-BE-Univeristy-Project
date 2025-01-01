package com.fmi.discord.mappers;

import com.fmi.discord.entities.FriendMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMessageRowMapper implements RowMapper<FriendMessage> {
    @Override
    public FriendMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendMessage friendMessage = new FriendMessage();
        friendMessage.setId(rs.getInt("id"));
        friendMessage.setSenderId(rs.getInt("sender_id"));
        friendMessage.setReceiverId(rs.getInt("receiver_id"));
        friendMessage.setContent(rs.getString("content"));
        return friendMessage;
    }
}
