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
public class DeleteSqlValidator extends AbstractSqlValidator {

    //language=RegExp
    private static final String REGEX = "DELETE\\s+FROM\\s+\\w+\\s*(WHERE\\s+[^;]+)?(;\\s*)?";

    private final SqlValidator nestedQueriesValidator;

    public DeleteSqlValidator(@Qualifier("selectSqlValidator") SqlValidator nestedQueriesValidator) {
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
        return SqlQueryType.DELETE;
    }

    @Override
    protected boolean supportsNestedQueries() {
        return true;
    }
}
