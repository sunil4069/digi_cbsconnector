package com.digi.app.fundtransfer.controller;

import com.digi.app.component.CrudReturnService;
import com.digi.app.config.JspPathConfig;
import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.exception.ValidationErrorException;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.fundtransfer.repo.FundTransferTransactionRepository;
import com.digi.app.fundtransfer.service.FundTransferTransactionService;
import com.digi.app.masteraccounts.AccountTypeEnum;
import com.digi.app.masteraccounts.MasterAccounts;
import com.digi.app.masteraccounts.MasterAccountsService;
import com.digi.app.message.HttpResponses;
import com.digi.app.message.Messages;
import com.digi.app.util.PageTitles;
import com.digi.app.util.UtilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("transactions")
@Slf4j
public class FundTransferTransactionController {
    private FundTransferTransactionService fundTransferTransactionService;
    private FundTransferTransactionRepository fundTransferTransactionRepository;
    private CrudReturnService<FundTransferTransaction> crudReturnService;
    private UtilitiesService utilitiesService;
    private MasterAccountsService masterAccountsService;
    private static final String PAGE_TITLE = "pagetitle";

    @Autowired
    public void setMasterAccountsService(MasterAccountsService masterAccountsService) {
        this.masterAccountsService = masterAccountsService;
    }

    @Autowired
    public void setFundTransferTransactionService(FundTransferTransactionService fundTransferTransactionService) {
        this.fundTransferTransactionService = fundTransferTransactionService;
    }

    @Autowired
    public void setFundTransferTransactionRepository(FundTransferTransactionRepository fundTransferTransactionRepository) {
        this.fundTransferTransactionRepository = fundTransferTransactionRepository;
    }

    @Autowired
    public void setCrudReturnService(CrudReturnService<FundTransferTransaction> crudReturnService) {
        this.crudReturnService = crudReturnService;
    }

    @Autowired
    public void setUtilitiesServiceImpl(UtilitiesService utilitiesService) {
        this.utilitiesService = utilitiesService;
    }

    @GetMapping(value = "/create-form")
    public final ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.FUND_TRANSFER_CREATE_FORM);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        List<MasterAccounts> masterAccounts = masterAccountsService.findByAccountTypeIn(Collections.singletonList(String.valueOf(AccountTypeEnum.CURRENT)));
        if (!masterAccounts.isEmpty()) {
            modelAndView.addObject("debitAccountNumber", masterAccounts.get(0).getAccountNumber());
        }
        return modelAndView;
    }

    @GetMapping(value = "/unauthorized")
    public final ModelAndView unauthorizedTransactions() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.FUND_TRANSFER_UNAUTHORIZED_TABLE);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        return modelAndView;
    }

    @GetMapping(value = "/authorized")
    public final ModelAndView authorizedTransactions() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.FUND_TRANSFER_AUTHORIZED_TABLE);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        return modelAndView;
    }

    @GetMapping(value = "/all/unauthorized")
    public final ResponseEntity<?> findUnauthorizedTransactions() {
        String currentDate = utilitiesService.getCurrentDateInDigiDateFormat();
        List<FundTransferTransaction> list = fundTransferTransactionRepository.findAllByAuthorizedAndTransactionDate(0, currentDate);
        return crudReturnService.controllerReturnForList(list);
    }

    @GetMapping(value = "/all/authorized")
    public final ResponseEntity<?> findAuthorizedTransactions() {
        String currentDate = utilitiesService.getCurrentDateInDigiDateFormat();
        List<FundTransferTransaction> list = fundTransferTransactionRepository.findAllByAuthorizedAndTransactionDate(1, currentDate);
        return crudReturnService.controllerReturnForList(list);
    }

    @GetMapping(path = "/my/unauthorized")
    public final ResponseEntity<?> curUserUnauthorizedTransactionsJson(Principal principal) {
        List<FundTransferTransaction> list = fundTransferTransactionRepository.findByCreatedByAndAuthorized(principal.getName(), 0);
        return crudReturnService.controllerReturnForList(list);
    }

    @GetMapping(path = "/my/authorized")
    public final ResponseEntity<?> curUserAuthorizedTransactionsJson(Principal principal) {
        List<FundTransferTransaction> list = fundTransferTransactionRepository.findByCreatedByAndAuthorized(principal.getName(), 1);
        return crudReturnService.controllerReturnForList(list);
    }

    private MasterAccounts fundTransferValidation(FundTransferTransaction fundTransferTransaction) {
        if (fundTransferTransaction.getDrAccountNo().equals(fundTransferTransaction.getCrAccountNo())) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.DR_ACCOUNT_EQUALS_CR_ACCOUNT));
        }
        if (StringUtils.isBlank(fundTransferTransaction.getLoanAccountNumber())) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.LOAN_ACCOUNT_CANNOT_BE_EMPTY));
        }
        List<MasterAccounts> masterAccounts = masterAccountsService.findByAccountTypeIn(Collections.singletonList(String.valueOf(AccountTypeEnum.CURRENT)));
        if (masterAccounts.isEmpty() || StringUtils.isBlank(masterAccounts.get(0).getAccountNumber())) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.CURRENT_ACCOUNT_NOT_FOUND));
        } else {
            return masterAccounts.get(0);
        }
    }

    @PostMapping
    public final ResponseEntity<?> saveTransaction(@Validated @RequestBody FundTransferTransaction fundTransferTransaction, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult);
        }
        MasterAccounts masterCurrentAccounts = fundTransferValidation(fundTransferTransaction);
        fundTransferTransaction.setDrAccountNo(masterCurrentAccounts.getAccountNumber());
        Optional<FundTransferTransaction> savedFundTransferTransaction = fundTransferTransactionService.saveFundTransferTransaction(fundTransferTransaction, principal);
        return savedFundTransferTransaction.map(transferTransaction -> new ResponseEntity<>(HttpResponses.created(transferTransaction), HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST));

    }

    @GetMapping(path = "/{digiTransactionId}")
    public final ResponseEntity<?> getOneTransaction(@PathVariable String digiTransactionId) {
        try {
            Optional<FundTransferTransaction> fundTransferTransactionOptional = fundTransferTransactionRepository.findById(digiTransactionId);
            if (fundTransferTransactionOptional.isPresent()) {
                return new ResponseEntity<>(HttpResponses.fetched(fundTransferTransactionOptional.get()), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);

    }

    /**
     * 1. fundTransferTransaction must be present
     * 2. fundTransferTransaction must be created by the user who is deleting
     * 3. fundTransferTransaction must not have been authorized at the time of deleting
     *
     * @param digiTransactionId
     * @param principal
     * @param authentication
     * @return
     */
    @DeleteMapping(path = "/{digiTransactionId}")
    public final ResponseEntity<Messages> delete(@PathVariable String digiTransactionId, Principal principal, Authentication authentication) {
        Optional<FundTransferTransaction> fundTransferTransactionOptional = fundTransferTransactionRepository.findById(digiTransactionId);
        if (fundTransferTransactionOptional.isPresent() && StringUtils.isEmpty(fundTransferTransactionOptional.get().getAuthorizedBy()) && fundTransferTransactionOptional.get().getCreatedBy().equalsIgnoreCase(principal.getName())) {
            fundTransferTransactionRepository.delete(fundTransferTransactionOptional.get());
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/authorize/{digiTransactionId}")
    public final ResponseEntity<Messages> authorizeTransaction(@PathVariable String digiTransactionId, Principal principal) {
        return fundTransferTransactionService.authorizeFundTransferTransaction(digiTransactionId, principal);
    }
}
