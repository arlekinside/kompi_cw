package com.github.arlekinside.sqlver.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryDTO {

    private Long id;
    private boolean valid;
    private String query;
    private String date;

}
