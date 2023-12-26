package com.github.arlekinside.sqlver.logic.validation.impl;

import com.github.arlekinside.sqlver.logic.SqlQueryType;
import com.github.arlekinside.sqlver.logic.validation.AbstractSqlValidator;
import com.github.arlekinside.sqlver.logic.validation.SqlValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SelectSqlValidator extends AbstractSqlValidator {

    //language=RegExp
    private static final String REGEX =
            "SELECT\\s+[\\w\\s,.*]+\\s+" +                                                  // SELECT clause
            "FROM\\s+\\w+\\s*" +                                                            // FROM clause
            "(JOIN\\s+\\w+\\s+ON\\s+[\\w.]+\\s*=\\s*[\\w.]+\\s*)*" +                        // JOIN clauses
            "(WHERE\\s+(?:[^;]+(?:\\s+(?:AND|OR)\\s+)?|\\w+\\s+IN\\s+\\([^)]*\\))\\s*)?" +  // WHERE clause with AND/OR and IN
            "(ORDER\\s+BY\\s+[\\w.]+\\s*(ASC|DESC)?\\s*)?" + // ORDER BY clause
            "(;\\s*)?";

    @Override
    protected String getRexEx() {
        return REGEX;
    }

    @Override
    protected SqlValidator getNestedQueriesValidator() {
        return this;
    }

    @Override
    public SqlQueryType getQueryType() {
        return SqlQueryType.SELECT;
    }

    @Override
    protected boolean supportsNestedQueries() {
        return true;
    }
}
