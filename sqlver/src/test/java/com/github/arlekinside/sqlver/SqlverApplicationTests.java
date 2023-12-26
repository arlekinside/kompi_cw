package com.github.arlekinside.sqlver;

import com.github.arlekinside.sqlver.logic.validation.impl.SelectSqlValidator;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class SqlverApplicationTests {

    @Test
    void contextLoads() {

        SelectSqlValidator validator = new SelectSqlValidator();

        validator.validate("select * from (select * from table) "
                + "where name = 'one' and a='' and b='hello from bohdan''s' and var in (select * from (select * from (select * from table) where c='' and a='hell') where b='das''d')");
    }

}
