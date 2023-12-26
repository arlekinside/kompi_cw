package com.github.arlekinside.sqlver.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SqlValidationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User creatingUser;

    @Column(nullable = false)
    private Boolean valid = false;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(length = 1000)
    private String comment;

    public void setComment(String comment) {
        if (comment != null && comment.length() > 1000) {
            comment = comment.substring(0, 996) + "...";
        }
        this.comment = comment;
    }
}
