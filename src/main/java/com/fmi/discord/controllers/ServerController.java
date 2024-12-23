package com.fmi.discord.controllers;

import com.fmi.discord.entities.Server;
import com.fmi.discord.http.AppResponse;
import com.fmi.discord.services.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping("/servers")
    public ResponseEntity<?> createServer(@RequestBody Server server) {
        if (this.serverService.createServer(server)) {
            return AppResponse.success().withMessage("Server created successfully").build();
        }

        return AppResponse.error().withMessage("Server could not be created").build();
    }

    @GetMapping("/servers/{id}")
    public ResponseEntity<?> getServerById(@PathVariable int id) {
        Server serverResult = this.serverService.getServerById(id);

        if (serverResult == null) {
            return AppResponse.error().withMessage("Server data not found").build();
        }

        return AppResponse.success().withData(serverResult).build();
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<?> deleteServer(@PathVariable int id, @RequestParam int ownerId) {
        boolean isDeleted = this.serverService.deleteServer(id, ownerId);

        if (!isDeleted) {
            return AppResponse.error()
                    .withMessage("Server could not be deleted. Either it doesn't exist or you are not the owner.")
                    .build();
        }

        return AppResponse.success().withMessage("Server deleted successfully").build();
    }
}
