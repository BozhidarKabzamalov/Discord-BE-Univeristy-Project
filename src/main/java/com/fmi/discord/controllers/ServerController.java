package com.fmi.discord.controllers;

import com.fmi.discord.entities.Server;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.MembershipService;
import com.fmi.discord.services.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    private final ServerService serverService;
    private final MembershipService membershipService;

    public ServerController(ServerService serverService, MembershipService membershipService) {
        this.serverService = serverService;
        this.membershipService = membershipService;
    }

    @PostMapping("/servers")
    public ResponseEntity<?> createServer(@RequestBody Server server, @RequestHeader("User-Id") int userId) {
        if (this.serverService.createServer(server, userId)) {
            return AppResponse.success().withMessage("Server created successfully").build();
        }

        return AppResponse.error().withMessage("Server could not be created").build();
    }

    @GetMapping("/servers/{serverId}")
    public ResponseEntity<?> getServerById(@PathVariable int serverId) {
        Server serverResult = this.serverService.getServerById(serverId);

        if (serverResult == null) {
            return AppResponse.error().withMessage("Server data not found").build();
        }

        return AppResponse.success().withData(serverResult).build();
    }

    @DeleteMapping("/servers/{serverId}")
    public ResponseEntity<?> deleteServer(@PathVariable int serverId, @RequestHeader("User-Id") int userId) {
        boolean isUserIdServerOwner = this.membershipService.isUserIdServerOwner(serverId, userId);

        if (!isUserIdServerOwner) {
            return AppResponse.error()
                    .withMessage("Server could not be deleted.")
                    .build();
        }

        boolean isDeleted = this.serverService.deleteServer(serverId);

        if (!isDeleted) {
            return AppResponse.error()
                    .withMessage("Server could not be deleted.")
                    .build();
        }

        return AppResponse.success().withMessage("Server deleted successfully").build();
    }

    @PutMapping("/servers/{serverId}")
    public ResponseEntity<?> updateCar(@PathVariable int serverId, @RequestBody Server server, @RequestHeader("User-Id") int userId) {
        boolean isUserIdServerOwner = this.membershipService.isUserIdServerOwner(serverId, userId);

        if (!isUserIdServerOwner) {
            return AppResponse.error()
                    .withMessage("Server could not be updated.")
                    .build();
        }

        boolean isUpdateSuccessful =  this.serverService.updateServer(serverId, server);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Server data not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Update successful")
                .build();
    }
}
