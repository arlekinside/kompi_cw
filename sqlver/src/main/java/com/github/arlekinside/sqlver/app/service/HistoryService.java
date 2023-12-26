package com.github.arlekinside.sqlver.app.service;

import com.github.arlekinside.sqlver.app.dto.HistoryDTO;
import com.github.arlekinside.sqlver.app.dto.HistoryStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;

import java.util.List;

public interface HistoryService {
    List<HistoryDTO> getHistoryForUser(User user);

    HistoryStatsDTO getStats();
}
