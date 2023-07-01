package com.digi.app.fundtransfer.service;

import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.message.Messages;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Optional;

public interface FundTransferTransactionService {
    Optional<FundTransferTransaction> saveFundTransferTransaction(FundTransferTransaction fundTransferTransaction, Principal principal);

    boolean getTelnetFundTransferTransaction(@PathVariable String digiTransactionId, Principal principal);

    ResponseEntity<Messages> authorizeFundTransferTransaction(String digiTransactionId, Principal principal);

    Optional<FundTransferTransaction> findByDigiTransactionId(String digiTransactionID);
}
