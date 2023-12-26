package com.github.arlekinside.sqlver.app.repo;

import com.github.arlekinside.sqlver.app.entity.SqlValidationLog;
import com.github.arlekinside.sqlver.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlValidationLogRepository extends JpaRepository<SqlValidationLog, Long> {

    List<SqlValidationLog> findAllByCreatingUser(User creatingUser);

    long countAllByValid(boolean valid);

}
