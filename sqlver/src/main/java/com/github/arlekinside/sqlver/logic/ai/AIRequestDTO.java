package com.github.arlekinside.sqlver.logic.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AIRequestDTO {

    private String model;
    private List<AIMessage> messages;
    private double temperature; //0.2
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty; //2
    private int n; //1
    @JsonProperty("max_tokens")
    private int maxTokens; //50

}
