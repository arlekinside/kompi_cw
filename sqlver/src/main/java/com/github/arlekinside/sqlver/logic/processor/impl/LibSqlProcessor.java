package com.github.arlekinside.sqlver.logic.processor.impl;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.logic.Utils;
import com.github.arlekinside.sqlver.logic.processor.AbstractSqlProcessor;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.ValidationException;
import net.sf.jsqlparser.util.validation.feature.FeaturesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Order(2)
public class LibSqlProcessor extends AbstractSqlProcessor {

    @Override
    protected boolean handle(SqlValidationContext sqlValidationContext) {
        var errors = validate(sqlValidationContext);

        if (!errors.isEmpty()) {
            var message = errors.stream()
                    .map(e -> e.getErrors()
                            .stream()
                            .map(ValidationException::getMessage)
                            .collect(Collectors.joining(" | ")))
                    .collect(Collectors.joining("\n"));
            sqlValidationContext.setComment(message);
            return false;
        }

        return true;
    }

    private static List<ValidationError> validate(SqlValidationContext sqlValidationContext) {
        var sql = sqlValidationContext.getQuery();
        var multiQueries = new ArrayList<String>();
        sql = Utils.preProcess(sql);
        sql = Utils.splitPopFirst(sql, ";", multiQueries);

        var featuresAllowed = Arrays.asList(
                switch (sqlValidationContext.getQueryType()) {
                    case SELECT -> FeaturesAllowed.SELECT;
                    case INSERT -> FeaturesAllowed.INSERT;
                    case DELETE -> FeaturesAllowed.DELETE;
                    case UPDATE -> FeaturesAllowed.UPDATE;
                }
        );

        var errors = getValidationErrors(featuresAllowed, sql);
        if (!errors.isEmpty()) {
            return errors;
        }

        for(var query: multiQueries) {
            errors = getValidationErrors(featuresAllowed, query);
            if (!errors.isEmpty()) {
                return errors;
            }
        }

        return errors;
    }

    private static List<ValidationError> getValidationErrors(List<FeaturesAllowed> featuresAllowed, String sql) {
        var validation = new Validation(featuresAllowed, sql);
        return validation.validate();
    }
}
