package com.digi.app.report;

import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.fundtransfer.repo.FundTransferTransactionRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private FundTransferTransactionRepository fundTransferTransactionRepository;

    @Autowired
    public void setFundTransferTransactionRepository(FundTransferTransactionRepository fundTransferTransactionRepository) {
        this.fundTransferTransactionRepository = fundTransferTransactionRepository;
    }

    @Override
    public List<FundTransferTransaction> getAuthorizedTransactions(String dateFrom, String dateTo) {
        if (StringUtils.isBlank(dateTo) && StringUtils.isNotBlank(dateFrom)) {
            return fundTransferTransactionRepository.findByAuthorizedAndTransactionDateIsGreaterThanEqual(1, dateFrom);
        } else if (StringUtils.isNotBlank(dateTo) && StringUtils.isBlank(dateFrom)) {
            return fundTransferTransactionRepository.findByAuthorizedAndTransactionDateIsLessThanEqual(1, dateTo);
        } else if (StringUtils.isNotBlank(dateFrom) && StringUtils.isNotBlank(dateTo)) {
            return fundTransferTransactionRepository.findByAndAuthorizedAndTransactionDateBetween(1, dateFrom, dateTo);
        } else {
            return fundTransferTransactionRepository.findAllByAuthorized(1);
        }
    }
    
   
    
    
    
    
}
