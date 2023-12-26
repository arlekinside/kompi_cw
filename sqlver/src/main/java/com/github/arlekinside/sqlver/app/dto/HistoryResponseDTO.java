package com.github.arlekinside.sqlver.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class HistoryResponseDTO {

    private String username;
    private List<HistoryDTO> histories;

}
