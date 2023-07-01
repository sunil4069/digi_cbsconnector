package com.digi.app.report;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatementReportDto {
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
