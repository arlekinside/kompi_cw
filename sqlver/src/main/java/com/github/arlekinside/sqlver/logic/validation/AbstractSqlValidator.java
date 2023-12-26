package com.github.arlekinside.sqlver.logic.validation;

import com.github.arlekinside.sqlver.logic.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractSqlValidator implements SqlValidator {

    protected static final String NESTED = "tabbl";

    protected static final List<String> UNSUPPORTED = List.of(".");

    @Override
    public final boolean validate(String sql) {
        sql = Utils.nvl(Utils.preProcess(sql), "");
        if (sql.isBlank()) {
            return false;
        }
        var nestedQueries = new ArrayList<String>();

        if (supportsNestedQueries()) {
            sql = Utils.extractAndReplace(sql, nestedQueries, '(', ')', NESTED);
        }

        sql = Utils.splitPopFirst(sql, ";", nestedQueries);

        if (UNSUPPORTED.stream().anyMatch(sql::contains)) {
            return true;
        }
        if (!Pattern.matches(getRexEx(), sql)) {
            return false;
        }

        for(var nested: nestedQueries) {
            if (UNSUPPORTED.stream().anyMatch(nested::contains)) {
                return true;
            }
            if (!getNestedQueriesValidator().validate(nested)) {
                return false;
            }
        }
        return true;
    }

    protected abstract String getRexEx();

    protected abstract SqlValidator getNestedQueriesValidator();

    protected abstract boolean supportsNestedQueries();
}
