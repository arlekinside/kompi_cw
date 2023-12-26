package com.github.arlekinside.sqlver.app.controller;

import com.github.arlekinside.sqlver.app.dto.HistoryResponseDTO;
import com.github.arlekinside.sqlver.app.dto.HistoryStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<HistoryResponseDTO> getAllHistory(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var history = historyService.getHistoryForUser(user);

        var res = new HistoryResponseDTO();
        res.setUsername(user.getUsername());
        res.setHistories(history);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/stats")
    public ResponseEntity<HistoryStatsDTO> getStats() {
        var res = historyService.getStats();
        return ResponseEntity.ok(res);
    }
}
