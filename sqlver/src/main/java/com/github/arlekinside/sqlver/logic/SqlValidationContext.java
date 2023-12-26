package com.github.arlekinside.sqlver.logic;

import com.github.arlekinside.sqlver.app.entity.User;
import lombok.Data;

@Data
public class SqlValidationContext {

    private String query;
    private User user;
    private SqlQueryType queryType;

    private boolean isValid = false;
    private String comment;

}
