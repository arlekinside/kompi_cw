package com.github.arlekinside.sqlver.app.service.impl;

import com.github.arlekinside.sqlver.app.dto.HistoryDTO;
import com.github.arlekinside.sqlver.app.dto.HistoryStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.repo.SqlValidationLogRepository;
import com.github.arlekinside.sqlver.app.service.HistoryService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final SqlValidationLogRepository validationLogRepository;

    public HistoryServiceImpl(SqlValidationLogRepository validationLogRepository) {
        this.validationLogRepository = validationLogRepository;
    }

    @Override
    public List<HistoryDTO> getHistoryForUser(User user) {
        var res = validationLogRepository.findAllByCreatingUser(user);

        return res.stream()
                .sorted((h1, h2) -> {
                    var h1d = h1.getDateCreated();
                    var h2d = h2.getDateCreated();

                    if (h1d.equals(h2d)) {
                        return 0;
                    }
                    if (h1d.isAfter(h2d)) {
                        return -1;
                    }
                    return 1;
                })
                .map(r -> new HistoryDTO(
                            r.getId(), r.getValid(), r.getQuery(), r.getDateCreated().format(DateTimeFormatter.ISO_DATE)
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public HistoryStatsDTO getStats() {
        var res = new HistoryStatsDTO();
        var total = validationLogRepository.count();
        var correct = validationLogRepository.countAllByValid(true);

        res.setCorrectness((long)(((double) correct / total) * 100));

        return res;
    }
}
