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
public class InsertSqlValidator extends AbstractSqlValidator {

    //language=RegExp
    private static final String REGEX = "INSERT\\s+INTO\\s+\\w+\\s*\\((\\s*\\w+(\\s*,\\s*\\w+)*\\s*)\\)\\s+VALUES\\s*\\((\\s*('[^']*'|\\w+)(\\s*,\\s*('[^']*'|\\w+))*\\s*)\\)(?:\\s*;\\s*)?";

    @Override
    protected String getRexEx() {
        return REGEX;
    }

    @Override
    protected SqlValidator getNestedQueriesValidator() {
        return null;
    }

    @Override
    public SqlQueryType getQueryType() {
        return SqlQueryType.INSERT;
    }

    @Override
    protected boolean supportsNestedQueries() {
        return false;
    }
}
