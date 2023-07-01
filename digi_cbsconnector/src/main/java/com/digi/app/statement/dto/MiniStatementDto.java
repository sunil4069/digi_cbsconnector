package com.digi.app.statement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniStatementDto {
    private String accountNumber;
    private String accountName;
    private String bookingDateEn;
    private String bookingDateNp;
    private String valueDateEn;
    private String transactionId;
    private String digiTransactionId;
    private String narrative;
    private String debitAmount;
    private String creditAmount;
    private String balance;
    private String transactionType;
    private String chequeNo;
    private String amount;
}
