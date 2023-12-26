package com.github.arlekinside.sqlver.logic.validation;

import com.github.arlekinside.sqlver.logic.SqlQueryType;

public interface SqlValidator {

    boolean validate(String sql);

    SqlQueryType getQueryType();

}
