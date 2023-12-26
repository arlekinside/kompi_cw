package com.github.arlekinside.sqlver.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SqlValidateDTO {

    @NotBlank
    @Pattern(regexp = "^(?s)((?!((<\\s*script[^>]*>(.*?)<\\s*/\\s*script\\s*>)|(<[^>]+\\s*(on\\w+|style|xmlns)\\s*=\\s*[^>]*>)|(javascript:\\s*\\S+)|(vbscript:\\s*\\S+)|(\\b(alert|confirm|prompt|msgbox)\\s*\\([^)]*\\)))).)*$")
    private String query;

    @NotBlank
    @Pattern(regexp = "(select|insert|update|delete)")
    private String queryType;

    @Pattern(regexp = "^https?://[\\w\\-]+(\\.[\\w\\-]+)?(:\\d{0,4})?$")
    private String webhookUrl;

    private boolean isValid;
    private String comment;
}
