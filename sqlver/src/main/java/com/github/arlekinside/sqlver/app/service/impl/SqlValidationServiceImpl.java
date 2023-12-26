package com.github.arlekinside.sqlver.app.service.impl;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.app.entity.SqlValidationLog;
import com.github.arlekinside.sqlver.logic.processor.SqlProcessorChain;
import com.github.arlekinside.sqlver.app.repo.SqlValidationLogRepository;
import com.github.arlekinside.sqlver.app.service.SqlValidationService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SqlValidationServiceImpl implements SqlValidationService {

    private final SqlProcessorChain sqlProcessorChain;
    private final SqlValidationLogRepository validationLogRepository;

    public SqlValidationServiceImpl(SqlProcessorChain sqlProcessorChain, SqlValidationLogRepository validationLogRepository) {
        this.sqlProcessorChain = sqlProcessorChain;
        this.validationLogRepository = validationLogRepository;
    }

    @Override
    public SqlValidationContext validate(SqlValidationContext sqlValidationContext) {
        sqlValidationContext = sqlProcessorChain.process(sqlValidationContext);
        createLog(sqlValidationContext);
        return sqlValidationContext;
    }

    private void createLog(SqlValidationContext sqlValidationContext) {
        var log = new SqlValidationLog();
        log.setValid(sqlValidationContext.isValid());
        log.setQuery(sqlValidationContext.getQuery());
        log.setComment(sqlValidationContext.getComment());
        log.setCreatingUser(sqlValidationContext.getUser());
        log.setDateCreated(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        validationLogRepository.save(log);
    }
}
