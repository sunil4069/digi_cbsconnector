package com.digi.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * UserActionLog contains the log that connects user actions with telnet/cbs connection
 */
@Getter
@Setter
@Table
@Entity
public class UserActionLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String actionType;
    @Column(columnDefinition = "LONGTEXT")
    private String request;
    @Column(columnDefinition = "LONGTEXT")
    private String response;
}
