package com.github.arlekinside.sqlver.logic.processor;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;


public abstract class AbstractSqlProcessor implements SqlProcessor {

    private SqlProcessor next;

    @Override
    public final SqlValidationContext process(SqlValidationContext sqlValidationContext) {
        var valid = this.handle(sqlValidationContext);
        if (!valid) {
            sqlValidationContext.setValid(false);
            return sqlValidationContext;
        }
        if (next != null) {
            sqlValidationContext = this.next.process(sqlValidationContext);
            if (!sqlValidationContext.isValid()) {
                return sqlValidationContext;
            }
        }
        sqlValidationContext.setValid(true);
        return sqlValidationContext;
    }

    @Override
    public final void setNext(SqlProcessor sqlProcessor) {
        this.next = sqlProcessor;
    }

    protected abstract boolean handle(SqlValidationContext sqlValidationContext);

}
