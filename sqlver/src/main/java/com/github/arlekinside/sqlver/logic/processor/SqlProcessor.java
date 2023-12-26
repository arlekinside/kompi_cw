package com.github.arlekinside.sqlver.logic.processor;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;

public interface SqlProcessor {

    SqlValidationContext process(SqlValidationContext sqlValidationContext);

    void setNext(SqlProcessor sqlProcessor);

}
