package com.digi.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "paymentrequest")
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private int transactionNumber;
    private int dayTransactionNumber;
    private String trDateEn;
    private String trDateNp;
    private int refStaffId;
    private String staffName;
    private String payAccountNumber;
    private String savingAccountNumber;
    private double paymentAmount;
    private String payType;
    private int status = 0;
}
