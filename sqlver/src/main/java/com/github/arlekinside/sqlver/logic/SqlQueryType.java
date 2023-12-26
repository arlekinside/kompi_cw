package com.github.arlekinside.sqlver.logic;

import java.util.Arrays;

public enum SqlQueryType {

    SELECT, INSERT, DELETE, UPDATE;

    public static SqlQueryType fromName(String name) {
        return Arrays.stream(SqlQueryType.values())
                .filter(n -> n.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
