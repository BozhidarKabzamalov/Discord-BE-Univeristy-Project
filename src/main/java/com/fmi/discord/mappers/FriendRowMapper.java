package com.fmi.discord.mappers;

import com.fmi.discord.entities.Friend;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRowMapper implements RowMapper<Friend> {
    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friend friend = new Friend();
        friend.setFriendId(rs.getInt("friend_id"));
        friend.setFriendUsername(rs.getString("friend_username"));
        return friend;
    }
}
