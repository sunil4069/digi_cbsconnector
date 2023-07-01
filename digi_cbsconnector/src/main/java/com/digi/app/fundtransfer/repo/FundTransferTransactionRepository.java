package com.digi.app.fundtransfer.repo;

import com.digi.app.fundtransfer.entities.FundTransferTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface FundTransferTransactionRepository extends JpaRepository<FundTransferTransaction, String> {

    List<FundTransferTransaction> findByAuthorizedAndTransactionDateIsGreaterThanEqual(int authorized, String dateFrom);

    List<FundTransferTransaction> findByAuthorizedAndTransactionDateIsLessThanEqual(int authorized, String dateTo);

    List<FundTransferTransaction> findByAndAuthorizedAndTransactionDateBetween(int authorized, String dateFrom, String dateTo);

    List<FundTransferTransaction> findByCreatedByAndAuthorized(String createdBy, int authorized);

    List<FundTransferTransaction> findAllByAuthorizedAndTransactionDate(int authorized, String transactionDate);

    List<FundTransferTransaction> findAllByAuthorized(int authorized);
    Optional<FundTransferTransaction> findByDigiTransactionId(String digiTransactionID);
}
