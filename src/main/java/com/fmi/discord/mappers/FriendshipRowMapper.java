package com.fmi.discord.mappers;

import com.fmi.discord.entities.Friendship;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipRowMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setId(rs.getInt("id"));
        friendship.setUserId(rs.getInt("user_id"));
        friendship.setFriendId(rs.getInt("friend_id"));
        return friendship;
    }
}
