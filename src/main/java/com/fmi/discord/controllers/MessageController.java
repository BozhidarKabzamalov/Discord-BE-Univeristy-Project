package com.fmi.discord.controllers;

import com.fmi.discord.entities.FriendMessage;
import com.fmi.discord.entities.Server;
import com.fmi.discord.entities.ServerMessage;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.FriendshipService;
import com.fmi.discord.services.MembershipService;
import com.fmi.discord.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/servers/messages")
    public ResponseEntity<?> createServerMessage(@RequestBody ServerMessage serverMessage, @RequestHeader("User-Id") int userId) {
        boolean isUserServerMember = this.membershipService.isUserServerMember(serverMessage.getServerId(), userId);

        if (!isUserServerMember) {
            return AppResponse.error()
                    .withMessage("Server message could not be created because the user is not a member of this server")
                    .build();
        }

        boolean isMessageCreatedSuccessfully = this.messageService.createServerMessage(serverMessage, userId);

        if (!isMessageCreatedSuccessfully) {
            return AppResponse.error().withMessage("Server message could not be created").build();
        }

        return AppResponse.success().withMessage("Server message created successfully").build();
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createFriendMessage(@RequestBody FriendMessage friendMessage, @RequestHeader("User-Id") int userId) {
        boolean isUserAlreadyFriend = this.friendshipService.isUserAlreadyFriend(userId, friendMessage.getReceiverId());

        if (!isUserAlreadyFriend) {
            return AppResponse.error()
                    .withMessage("Friend message could not be created because the receiver is not a friend")
                    .build();
        }

        boolean isMessageCreatedSuccessfully = this.messageService.createFiendMessage(friendMessage, userId);

        if (!isMessageCreatedSuccessfully) {
            return AppResponse.error().withMessage("Friend message could not be created").build();
        }

        return AppResponse.success().withMessage("Friend message created successfully").build();
    }
}
