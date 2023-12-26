package com.github.arlekinside.sqlver.app.controller;

import com.github.arlekinside.sqlver.app.dto.UserDTO;
import com.github.arlekinside.sqlver.app.dto.UserStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.exception.UserExistsException;
import com.github.arlekinside.sqlver.app.service.HistoryService;
import com.github.arlekinside.sqlver.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HistoryService historyService;

    public UserController(UserService userService, HistoryService historyService) {
        this.userService = userService;
        this.historyService = historyService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        try {
            var user = userService.registerUser(userDTO);
            userDTO.setPassword(null);
            return ResponseEntity.ok(userDTO);
        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<UserStatsDTO> getStats(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var res = userService.getStats();
        res.setUsername(user.getUsername());
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        var history = historyService.getHistoryForUser(user);

        var res = new UserDTO();
        res.setUsername(user.getUsername());
        res.setHistories(history);
        res.setWebhookUrl(user.getWebhookUrl());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> updateWebhook(@RequestBody UserDTO userDTO, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        user.setWebhookUrl(userDTO.getWebhookUrl());

        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
