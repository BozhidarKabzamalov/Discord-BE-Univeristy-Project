package com.fmi.discord.controllers;

import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @DeleteMapping("/servers/{serverId}/members/{userId}")
    public ResponseEntity<?> removeUserFromServer(@PathVariable int serverId, @PathVariable int userId, @RequestParam int ownerId) {
        boolean isRemoved = this.membershipService.removeUserFromServer(serverId, ownerId, userId);

        if (!isRemoved) {
            return AppResponse.error()
                    .withMessage("User could not be removed. Either the server does not exist, you are not the owner, or the user is not a member.")
                    .build();
        }

        return AppResponse.success().withMessage("User removed from the server successfully").build();
    }
}
