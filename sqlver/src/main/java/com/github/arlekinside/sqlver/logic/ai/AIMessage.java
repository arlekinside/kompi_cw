package com.github.arlekinside.sqlver.logic.ai;

import lombok.Data;

@Data
public class AIMessage {
    private String role; //user
    private String content;
}
