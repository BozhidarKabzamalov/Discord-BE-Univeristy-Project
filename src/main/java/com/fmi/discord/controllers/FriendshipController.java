package com.fmi.discord.controllers;

import com.fmi.discord.entities.Friendship;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/friendships")
    public ResponseEntity<?> addFriend(@RequestBody Friendship friendship, @RequestHeader("User-Id") int userId) {
        boolean isUserAlreadyFriend = friendshipService.isUserAlreadyFriend(userId, friendship.getFriendId());

        if (isUserAlreadyFriend) {
            return AppResponse.error().withMessage("User is already a friend").build();
        }

        boolean success = friendshipService.addFriend(userId, friendship.getFriendId());

        if (success) {
            return AppResponse.success().withMessage("Friend added successfully").build();
        }

        return AppResponse.error().withMessage("Failed to add friend").build();
    };
}
