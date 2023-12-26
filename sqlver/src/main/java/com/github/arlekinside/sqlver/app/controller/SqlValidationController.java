package com.github.arlekinside.sqlver.app.controller;

import com.github.arlekinside.sqlver.app.dto.SqlValidateDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.service.SqlValidationService;
import com.github.arlekinside.sqlver.app.service.WebhookService;
import com.github.arlekinside.sqlver.logic.SqlQueryType;
import com.github.arlekinside.sqlver.logic.SqlValidationContext;
import com.github.arlekinside.sqlver.logic.Utils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql")
public class SqlValidationController {

    private final SqlValidationService validationService;
    private final WebhookService webHookService;

    public SqlValidationController(SqlValidationService validationService, WebhookService webHookService) {
        this.validationService = validationService;
        this.webHookService = webHookService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SqlValidateDTO> validateSql(@RequestBody @Validated SqlValidateDTO sqlValidateDTO, Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        var query = sqlValidateDTO.getQuery();
        var queryType = SqlQueryType.fromName(sqlValidateDTO.getQueryType());
        var webhookUrl = user.getWebhookUrl();

        var context = new SqlValidationContext();
        context.setQuery(query);
        context.setQueryType(queryType);
        context.setUser(user);

        context = validationService.validate(context);

        if (!Utils.nvl(webhookUrl, "").isBlank()) {
            webHookService.send(webhookUrl, context);
        }

        sqlValidateDTO.setValid(context.isValid());
        sqlValidateDTO.setComment(context.getComment());
        return ResponseEntity.ok(sqlValidateDTO);
    }

}
