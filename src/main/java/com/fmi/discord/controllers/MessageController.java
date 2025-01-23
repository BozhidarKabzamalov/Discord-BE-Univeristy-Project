package com.fmi.discord.controllers;

import com.fmi.discord.entities.*;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.FriendshipService;
import com.fmi.discord.services.MembershipService;
import com.fmi.discord.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private final MessageService messageService;
    private final MembershipService membershipService;
    private final FriendshipService friendshipService;

    public MessageController(MessageService messageService, MembershipService membershipService, FriendshipService friendshipService) {
        this.messageService = messageService;
        this.membershipService = membershipService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/servers/{serverId}/messages")
    public ResponseEntity<?> getServerMessages(@PathVariable int serverId) {
        List<ServerMessageDTO> serverMessages = this.messageService.getServerMessages(serverId);

        return AppResponse.success().withData(serverMessages).build();
    }

    @GetMapping("/friends/{friendId}/messages")
    public ResponseEntity<?> getFriendMessages(@PathVariable int friendId, @RequestHeader("User-Id") int userId) {
        List<FriendMessageDTO> friendMessages = this.messageService.getFriendMessages(friendId, userId);

        return AppResponse.success().withData(friendMessages).build();
    }

    @PostMapping("/servers/messages")
    public ResponseEntity<?> createServerMessage(@RequestBody ServerMessage serverMessage, @RequestHeader("User-Id") int userId) {
        boolean isUserServerMember = this.membershipService.isUserServerMember(serverMessage.getServerId(), userId);

        if (!isUserServerMember) {
            return AppResponse.error()
                    .withMessage("Server message could not be created because the user is not a member of this server")
                    .build();
        }

        ServerMessageDTO message = this.messageService.createServerMessage(serverMessage, userId);

        if (message == null) {
            return AppResponse.error().withMessage("Server message could not be created").build();
        }

        return AppResponse.success().withMessage("Server message created successfully").withData(message).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createFriendMessage(@RequestBody FriendMessage friendMessage, @RequestHeader("User-Id") int userId) {
        boolean isUserAlreadyFriend = this.friendshipService.isUserAlreadyFriend(userId, friendMessage.getReceiverId());

        if (!isUserAlreadyFriend) {
            return AppResponse.error()
                    .withMessage("Friend message could not be created because the receiver is not a friend")
                    .build();
        }

        FriendMessageDTO message = this.messageService.createFriendMessage(friendMessage, userId);

        if (message == null) {
            return AppResponse.error().withMessage("Friend message could not be created").build();
        }

        return AppResponse.success().withMessage("Friend message created successfully").withData(message).build();
    }
}
