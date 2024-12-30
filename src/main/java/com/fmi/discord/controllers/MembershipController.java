package com.fmi.discord.controllers;

import com.fmi.discord.entities.Server;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/servers/{serverId}/memberships/{targetUserId}/add")
    public ResponseEntity<?> addGuestUserToServer(@PathVariable int serverId, @PathVariable int targetUserId, @RequestHeader("User-Id") int userId) {
        boolean isUserIdServerOwnerOrAdmin = this.membershipService.isUserIdServerOwnerOrAdmin(serverId, userId);

        if (!isUserIdServerOwnerOrAdmin) {
            return AppResponse.error()
                    .withMessage("User is not owner or admin")
                    .build();
        }

        boolean isUpdateSuccessful = this.membershipService.addGuestUserToServer(serverId, targetUserId);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Could not add user to server")
                    .build();
        }

        return AppResponse.success()
                .withMessage("User added to server successfully")
                .build();
    }

    @PutMapping("/servers/{serverId}/memberships/{targetUserId}/promote")
    public ResponseEntity<?> promoteUserToAdmin(@PathVariable int serverId, @PathVariable int targetUserId, @RequestHeader("User-Id") int userId) {
        boolean isUserIdServerOwner = this.membershipService.isUserIdServerOwner(serverId, userId);

        if (!isUserIdServerOwner) {
            return AppResponse.error()
                    .withMessage("Promoter is not server owner")
                    .build();
        }

        boolean isUserServerMember = this.membershipService.isUserServerMember(serverId, targetUserId);

        if (!isUserServerMember) {
            return AppResponse.error()
                    .withMessage("Target user is not part of the server")
                    .build();
        }

        boolean isUpdateSuccessful = this.membershipService.promoteUserToAdmin(serverId, targetUserId);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Could not promote user")
                    .build();
        }

        return AppResponse.success()
                .withMessage("User promotion successful")
                .build();
    }
}
