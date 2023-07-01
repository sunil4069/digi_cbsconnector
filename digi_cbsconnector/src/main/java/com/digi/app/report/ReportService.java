package com.digi.app.report;

import com.digi.app.fundtransfer.entities.FundTransferTransaction;

import java.util.List;

public interface ReportService {
    List<FundTransferTransaction> getAuthorizedTransactions(String dateFrom, String dateTo);
    
}
