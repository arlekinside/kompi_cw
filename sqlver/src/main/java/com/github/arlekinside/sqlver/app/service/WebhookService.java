package com.github.arlekinside.sqlver.app.service;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;

public interface WebhookService {

    void send(String url, SqlValidationContext sqlValidationContext);
}
