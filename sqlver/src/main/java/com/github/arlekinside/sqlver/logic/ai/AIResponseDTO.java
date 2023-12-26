package com.github.arlekinside.sqlver.logic.ai;

import lombok.Data;

import java.util.List;

@Data
public class AIResponseDTO {

    private List<Choice> choices;

    @Data
    public static class Choice {

        private int index;
        private AIMessage message;

    }
}
