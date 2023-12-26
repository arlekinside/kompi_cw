package com.github.arlekinside.sqlver.app.config;

import com.github.arlekinside.sqlver.logic.processor.AbstractSqlProcessor;
import com.github.arlekinside.sqlver.logic.processor.SqlProcessorChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SqlProcessorConfig {

    private final List<AbstractSqlProcessor> sqlProcessors;

    public SqlProcessorConfig(List<AbstractSqlProcessor> sqlProcessors) {
        this.sqlProcessors = sqlProcessors;
    }

    @Bean
    public SqlProcessorChain sqlProcessorChain() {
        var first = new SqlProcessorChain(sqlProcessors.get(0));

        for (var i = 0; i < sqlProcessors.size(); i++) {
            if (i + 1 < sqlProcessors.size()) {
                sqlProcessors.get(i).setNext(sqlProcessors.get(i + 1));
            }
        }

        return first;
    }
}
