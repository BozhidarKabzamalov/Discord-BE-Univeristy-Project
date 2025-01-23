package com.fmi.discord.mappers;

import com.fmi.discord.entities.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setUserId(rs.getInt("user_id"));
        member.setUsername(rs.getString("username"));
        member.setRole(rs.getString("role"));
        return member;
    }
}
