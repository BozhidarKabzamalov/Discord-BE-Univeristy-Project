package com.fmi.discord.services;

@Service
public class MembershipService {
    private final JdbcTemplate db;

    public MembershipService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean removeUserFromServer(int serverId, int ownerId, int userId) {
        // Validate ownership
        String validateOwnerQuery = "SELECT COUNT(*) FROM SERVERS WHERE id = ? AND owner_id = ?";
        int ownerCount = this.db.queryForObject(validateOwnerQuery, Integer.class, serverId, ownerId);

        if (ownerCount == 0) {
            return false; // The requester is not the owner of the server
        }

        // Remove the user from the server
        String removeMembershipQuery = "DELETE FROM MEMBERSHIPS WHERE server_id = ? AND user_id = ?";
        int rowsAffected = this.db.update(removeMembershipQuery, serverId, userId);

        return rowsAffected > 0; // Return true if a membership was removed
    }
}