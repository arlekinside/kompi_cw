package com.github.arlekinside.sqlver.logic.processor.impl;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.logic.factory.SqlValidatorFactory;
import com.github.arlekinside.sqlver.logic.processor.AbstractSqlProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Order(1)
public class BasicSqlProcessor extends AbstractSqlProcessor {

    private final SqlValidatorFactory validatorFactory;

    public BasicSqlProcessor(SqlValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Override
    protected boolean handle(SqlValidationContext sqlValidationContext) {
        var validator = validatorFactory.createValidator(sqlValidationContext.getQueryType());
        return validator.validate(sqlValidationContext.getQuery());
    }
}
