package com.fmi.discord.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
    private final JdbcTemplate db;

    public MembershipService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean removeUserFromServer(int serverId, int ownerId, int userId) {
        String validateOwnerQuery = "SELECT COUNT(*) FROM SERVERS WHERE id = ? AND owner_id = ?";
        int ownerCount = this.db.queryForObject(validateOwnerQuery, Integer.class, serverId, ownerId);

        if (ownerCount == 0) {
            return false;
        }

        String removeMembershipQuery = "DELETE FROM MEMBERSHIPS WHERE server_id = ? AND user_id = ?";
        int rowsAffected = this.db.update(removeMembershipQuery, serverId, userId);

        return rowsAffected > 0;
    }
}