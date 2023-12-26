package com.github.arlekinside.sqlver.logic.validation.impl;

import com.github.arlekinside.sqlver.logic.SqlQueryType;
import com.github.arlekinside.sqlver.logic.validation.AbstractSqlValidator;
import com.github.arlekinside.sqlver.logic.validation.SqlValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateSqlValidator extends AbstractSqlValidator {

    //language=RegExp
    private static final String REGEX = "UPDATE\\s+\\w+\\s+SET\\s+(\\w+\\s*=\\s*[^,;]+)(,\\s*\\w+\\s*=\\s*[^,;]+)*\\s*(WHERE\\s+[^;]+)?(;\\s*)?";

    private final SqlValidator nestedQueriesValidator;

    public UpdateSqlValidator(@Qualifier("selectSqlValidator") SqlValidator nestedQueriesValidator) {
        this.nestedQueriesValidator = nestedQueriesValidator;
    }

    @Override
    protected String getRexEx() {
        return REGEX;
    }

    @Override
    protected SqlValidator getNestedQueriesValidator() {
        return nestedQueriesValidator;
    }

    @Override
    public SqlQueryType getQueryType() {
        return SqlQueryType.UPDATE;
    }

    @Override
    protected boolean supportsNestedQueries() {
        return true;
    }
}
