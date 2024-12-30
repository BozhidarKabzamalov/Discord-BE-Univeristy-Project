package com.fmi.discord.services;

import com.fmi.discord.entities.Membership;
import com.fmi.discord.mappers.MembershipRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final JdbcTemplate db;

    public MembershipService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean isUserIdServerOwner(int serverId, int userId) {
        String query = "SELECT * FROM MEMBERSHIPS WHERE user_id = ? AND server_id = ? AND role_id = 2";
        List<Membership> collection = this.db.query(query, new MembershipRowMapper(), userId, serverId);

        return !collection.isEmpty();
    }

    public boolean isUserIdServerOwnerOrAdmin(int serverId, int userId) {
        String query = "SELECT * FROM MEMBERSHIPS WHERE user_id = ? AND server_id = ? AND (role_id = 2 OR role_id = 3)";
        List<Membership> collection = this.db.query(query, new MembershipRowMapper(), userId, serverId);

        return !collection.isEmpty();
    }

    public boolean isUserServerMember(int serverId, int userId) {
        String query = "SELECT * FROM MEMBERSHIPS WHERE user_id = ? AND server_id = ?";
        List<Membership> collection = this.db.query(query, new MembershipRowMapper(), userId, serverId);

        return !collection.isEmpty();
    }

    public boolean promoteUserToAdmin(int serverId, int userId) {
        String query = "UPDATE MEMBERSHIPS SET role_id = 3 WHERE user_id = ? AND server_id = ?";
        this.db.update(query, userId, serverId);

        return true;
    }

    public boolean addGuestUserToServer(int serverId, int userId) {
        String query = "INSERT INTO MEMBERSHIPS (user_id, server_id, role_id) VALUES (?, ?, ?)";
        this.db.update(query, userId, serverId, 1);

        return true;
    }
}