package com.digi.app.fundtransfer.controller;

import com.digi.app.config.JspPathConfig;
import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.ValidationErrorException;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.fundtransfer.service.FundTransferTransactionService;
import com.digi.app.masteraccounts.AccountTypeEnum;
import com.digi.app.masteraccounts.MasterAccountsService;
import com.digi.app.message.HttpResponses;
import com.digi.app.util.PageTitles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("accounts/transfers")
public class AccountTransferController {
    public static final String PAGE_TITLE = "pagetitle";
    private MasterAccountsService masterAccountsService;
    private FundTransferTransactionService fundTransferTransactionService;

    @Autowired
    public void setFundTransferTransactionService(FundTransferTransactionService fundTransferTransactionService) {
        this.fundTransferTransactionService = fundTransferTransactionService;
    }

    @Autowired
    public void setMasterAccountsService(MasterAccountsService masterAccountsService) {
        this.masterAccountsService = masterAccountsService;
    }

    @GetMapping(value = "/create-form")
    public final ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.ACCOUNT_TRANSFER_CREATE_FORM);
        modelAndView.addObject(PAGE_TITLE, PageTitles.ACCOUNT_TRANSFER);
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add(String.valueOf(AccountTypeEnum.CURRENT));
        accountTypes.add(String.valueOf(AccountTypeEnum.CALL));
        modelAndView.addObject("masterAccounts", masterAccountsService.findByAccountTypeIn(accountTypes));
        return modelAndView;
    }
    @PostMapping
    public final ResponseEntity<?> saveTransaction(@Validated @RequestBody FundTransferTransaction fundTransferTransaction, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult);
        }
        try {
            if (fundTransferTransaction.getDrAccountNo().equals(fundTransferTransaction.getCrAccountNo())) {
                return new ResponseEntity<>(HttpResponses.validationerror(Collections.singletonList(ErrorMessageConstants.DR_ACCOUNT_EQUALS_CR_ACCOUNT)), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            Optional<FundTransferTransaction> savedFundTransferTransaction = fundTransferTransactionService.saveFundTransferTransaction(fundTransferTransaction, principal);
            if (savedFundTransferTransaction.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedFundTransferTransaction.get()), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }


}
