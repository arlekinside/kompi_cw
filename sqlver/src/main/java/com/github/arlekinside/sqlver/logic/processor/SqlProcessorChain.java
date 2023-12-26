package com.github.arlekinside.sqlver.logic.processor;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;

public class SqlProcessorChain implements SqlProcessor {

    private final SqlProcessor first;

    public SqlProcessorChain(SqlProcessor first) {
        this.first = first;
    }

    @Override
    public SqlValidationContext process(SqlValidationContext sqlValidationContext) {
        return this.first.process(sqlValidationContext);
    }

    @Override
    public void setNext(SqlProcessor sqlProcessor) {
        throw new UnsupportedOperationException("[setNext] is not supported for SqlProcessorChain");
    }
}
