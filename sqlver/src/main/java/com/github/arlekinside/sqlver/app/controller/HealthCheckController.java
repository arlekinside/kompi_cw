package com.github.arlekinside.sqlver.app.controller;

import com.github.arlekinside.sqlver.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final UserRepository userRepository;
    private final String adminUsername;

    public HealthCheckController(UserRepository userRepository,
                                 @Value("${app.users.admin.username}") String adminUsername) {
        this.userRepository = userRepository;
        this.adminUsername = adminUsername;
    }

    @GetMapping
    public ResponseEntity<String> getHealth() {
        var now = Instant.now();
        var admin = userRepository.findByUsername(adminUsername);
        var delay = Duration.between(now, Instant.now()).toMillis();
        if (delay > 100) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
