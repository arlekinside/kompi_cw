package com.github.arlekinside.sqlver.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arlekinside.sqlver.app.service.WebhookService;
import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.logic.Utils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Service
public class WebhookServiceImpl implements WebhookService {

    @Override
    @Async
    public void send(String url, SqlValidationContext sqlValidationContext) {
        if (Utils.nvl(url, "").isBlank()) {
            return;
        }

        try (var client = HttpClient.newHttpClient()) {
            var req = HttpRequest.newBuilder(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(prepareRequest(sqlValidationContext)))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();
            var res = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String prepareRequest(SqlValidationContext sqlValidationContext) throws JsonProcessingException {
        var isValid = sqlValidationContext.isValid();
        var query = sqlValidationContext.getQuery();
        var queryType = sqlValidationContext.getQueryType();
        var comment = sqlValidationContext.getComment();
        var userId = sqlValidationContext.getUser().getId();

        var values = new HashMap<String, String>() {{
                put("isValid", String.valueOf(isValid));
                put("query", query);
                put("queryType", queryType.name());
                put("comment", comment);
                put("userId", String.valueOf(userId));
        }};

        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(values);
    }
}
