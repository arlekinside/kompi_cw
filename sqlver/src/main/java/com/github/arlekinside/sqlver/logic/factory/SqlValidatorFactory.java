package com.github.arlekinside.sqlver.logic.factory;

import com.github.arlekinside.sqlver.logic.SqlQueryType;
import com.github.arlekinside.sqlver.logic.validation.SqlValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlValidatorFactory {

    private final List<SqlValidator> sqlValidators;

    public SqlValidatorFactory(List<SqlValidator> sqlValidators) {
        this.sqlValidators = sqlValidators;
    }

    public SqlValidator createValidator(SqlQueryType queryType) {
        return sqlValidators.stream()
                .filter(v -> v.getQueryType().equals(queryType))
                .findAny()
                .orElseThrow();
    }

}
