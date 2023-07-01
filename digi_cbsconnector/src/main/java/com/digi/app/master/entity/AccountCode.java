package com.digi.app.master.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table
@Data
public class AccountCode {
@Id
private String accode;
private String achead;
private String mapacode;



}
