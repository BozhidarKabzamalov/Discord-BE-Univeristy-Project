package com.fmi.discord.mappers;

import com.fmi.discord.entities.Membership;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipRowMapper implements RowMapper<Membership> {
    @Override
    public Membership mapRow(ResultSet rs, int rowNum) throws SQLException {
        Membership membership = new Membership();
        membership.setId(rs.getInt("id"));
        membership.setUserId(rs.getInt("user_id"));
        membership.setServerId(rs.getInt("server_id"));
        membership.setRoleId(rs.getInt("role_id"));
        return membership;
    }
}
