package com.github.arlekinside.sqlver.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String username;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private List<HistoryDTO> histories;
    private String webhookUrl;

}
