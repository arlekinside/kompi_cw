package com.github.arlekinside.sqlver.logic.processor.impl;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.logic.ai.AIService;
import com.github.arlekinside.sqlver.logic.processor.AbstractSqlProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Order(3)
public class AISqlProcessor extends AbstractSqlProcessor {

    private final AIService aiService;

    private final boolean enabled;

    public AISqlProcessor(AIService aiService,
                          @Value("${app.ai.chatgpt.enabled}") boolean enabled) {
        this.aiService = aiService;
        this.enabled = enabled;
    }

    @Override
    protected boolean handle(SqlValidationContext sqlValidationContext) {
        if (!enabled) {
            return true;
        }
        var comment = aiService.requestAdvice(sqlValidationContext.getQuery());
        sqlValidationContext.setComment(comment);
        return true;
    }
}
