package com.digi.app.report;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundTransferReport {
    @CsvBindByName(column = "id")
    private String digiTransactionId;
    @CsvBindByName(column = "Dr Account No")
    private String drAccountNo;
    @CsvBindByName(column = "Cr Account No")
    private String crAccountNo;
    @CsvBindByName(column = "Transaction Date")
    private String transactionDate;
    @CsvBindByName(column = "Dr. narrative")
    private String drNarrative;
    @CsvBindByName(column = "Cr. narrative")
    private String crNarrative;
    @CsvBindByName(column = "amount")
    private String amount;
    @CsvBindByName(column = "transaction id")
    private String transactionId;
    @CsvBindByName(column = "created by")
    private String createdBy;
    @CsvBindByName(column = "authorized by")
    private String authorizedBy;
}
