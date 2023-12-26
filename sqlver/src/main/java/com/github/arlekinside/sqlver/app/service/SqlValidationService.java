package com.github.arlekinside.sqlver.app.service;

import com.github.arlekinside.sqlver.logic.SqlValidationContext;

public interface SqlValidationService {
    SqlValidationContext validate(SqlValidationContext sqlValidationContext);
}
