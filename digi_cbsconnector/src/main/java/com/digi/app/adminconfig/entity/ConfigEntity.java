package com.digi.app.adminconfig.entity;


import com.digi.app.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "config_entity")
public class ConfigEntity extends BaseEntity {
    @Id
    private int id = 1;
    private String telnetUsername;
    private String telnetPassword;
    private String telnetIp;
    private int telnetPort;
    @Column(columnDefinition = "LONGTEXT")
    private String fundTransferCommand;
    @Column(columnDefinition = "LONGTEXT")
    private String miniStatementCommand;
    @Column(columnDefinition = "LONGTEXT")
    private String statementCommand;

    public ConfigEntity() {
        if (this.getId() != 1) {
            this.setId(1);
        }
    }

}
