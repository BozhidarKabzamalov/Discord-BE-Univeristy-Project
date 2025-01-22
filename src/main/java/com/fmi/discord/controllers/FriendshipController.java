package com.fmi.discord.controllers;

import com.fmi.discord.entities.Friend;
import com.fmi.discord.entities.Friendship;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getFriendsOfUser(@RequestHeader("User-Id") int userId) {
        List<Friend> collection = friendshipService.getFriendsOfUser(userId);

        return AppResponse.success().withData(collection).build();
    };

    @PostMapping("/friendships")
    public ResponseEntity<?> addFriend(@RequestBody Friendship friendship, @RequestHeader("User-Id") int userId) {
        boolean isUserAlreadyFriend = friendshipService.isUserAlreadyFriend(userId, friendship.getFriendId());

        if (isUserAlreadyFriend) {
            return AppResponse.error().withMessage("User is already a friend").build();
        }

        Friend lastFriend = friendshipService.addFriend(userId, friendship.getFriendId());

        if (lastFriend == null) {
            return AppResponse.error().withMessage("Failed to add friend").build();
        }

        return AppResponse.success().withMessage("Friend added successfully").withData(lastFriend).build();
    };
}
