package com.digi.app.masteraccounts;

import com.digi.app.config.JspPathConfig;
import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.message.HttpResponses;
import com.digi.app.util.PageTitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("master/accounts")
public class MasterAccountsController {
    private final String PAGE_TITLE = "pagetitle";
    private MasterAccountsService masterAccountsService;

    @Autowired
    public void setMasterAccountsService(MasterAccountsService masterAccountsService) {
        this.masterAccountsService = masterAccountsService;
    }

    @GetMapping("/savings/create-form")
    private ModelAndView loanForm() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.MASTER_ACCOUNT_SAVINGS_CREATE_FORM);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        return modelAndView;
    }

    @GetMapping("/erfloans/create-form")
    private ModelAndView erfLoanForm() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.MASTER_ACCOUNT_ERF_LOAN_CREATE_FORM);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        return modelAndView;
    }

    @GetMapping("/pfloans/create-form")
    private ModelAndView pfLoanForm() {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.MASTER_ACCOUNT_PF_LOAN_CREATE_FORM);
        modelAndView.addObject(PAGE_TITLE, PageTitles.TRANSACTION);
        return modelAndView;
    }

    @PostMapping("/savings")
    public ResponseEntity<?> createMasterAccounts(@RequestBody MasterAccountsDto masterAccountsDto) {
        int id = masterAccountsDto.getId();
        Optional<MasterAccounts> masterAccounts = masterAccountsService.assembleLoanAccountToDomain(id, masterAccountsDto);
        if (masterAccounts.isPresent()) {
            Optional<MasterAccounts> savedMasterAccounts = masterAccountsService.save(masterAccounts.get());
            if (savedMasterAccounts.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedMasterAccounts.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pfloans")
    public ResponseEntity<?> createPfLoanMasterAccounts(@RequestBody MasterAccountsDto masterAccountsDto) {
        int id = masterAccountsDto.getId();
        Optional<MasterAccounts> masterAccounts = masterAccountsService.assemblePfLoanAccountToDomain(id, masterAccountsDto);
        if (masterAccounts.isPresent()) {
            Optional<MasterAccounts> savedMasterAccounts = masterAccountsService.save(masterAccounts.get());
            if (savedMasterAccounts.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedMasterAccounts.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/erfloans")
    public ResponseEntity<?> createErfLoanMasterAccounts(@RequestBody MasterAccountsDto masterAccountsDto) {
        int id = masterAccountsDto.getId();
        Optional<MasterAccounts> masterAccounts = masterAccountsService.assembleErfLoanAccountToDomain(id, masterAccountsDto);
        if (masterAccounts.isPresent()) {
            Optional<MasterAccounts> savedMasterAccounts = masterAccountsService.save(masterAccounts.get());
            if (savedMasterAccounts.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedMasterAccounts.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/savings")
    public ResponseEntity<?> findAllMasterAccounts() {
        List<MasterAccounts> masterAccounts = masterAccountsService.findByAccountTypeIn(Collections.singletonList(String.valueOf(AccountTypeEnum.SAVINGS)));
        if (!masterAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pfloans")
    public ResponseEntity<?> findAllPfMasterAccounts() {
        List<MasterAccounts> masterAccounts = masterAccountsService.findByAccountTypeIn(Collections.singletonList(String.valueOf(AccountTypeEnum.PFLOAN)));
        if (!masterAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/erfloans")
    public ResponseEntity<?> findAllErfMasterAccounts() {
        List<MasterAccounts> masterAccounts = masterAccountsService.findByAccountTypeIn(Collections.singletonList(String.valueOf(AccountTypeEnum.ERFLOAN)));
        if (!masterAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> findLoanAccountByAccountNumber(@PathVariable @NotNull String accountNumber) {
        Optional<MasterAccounts> masterAccountsOptional = masterAccountsService.findByAccountNumber(accountNumber);
        return masterAccountsOptional.map(masterAccounts -> new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.validationerror(Collections.singletonList(ErrorMessageConstants.SAVINGS_ACCOUNT_NOT_FOUND)), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/savings/{id}")
    public ResponseEntity<?> findLoan(@PathVariable int id) {
        Optional<MasterAccounts> masterAccountsOptional = masterAccountsService.findSavingsById(id);
        return masterAccountsOptional.map(masterAccounts -> new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/erfloans/{id}")
    public ResponseEntity<?> findErfLoan(@PathVariable int id) {
        Optional<MasterAccounts> masterAccountsOptional = masterAccountsService.findErfLoanById(id);
        return masterAccountsOptional.map(masterAccounts -> new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pfloans/{id}")
    public ResponseEntity<?> findPfLoan(@PathVariable int id) {
        Optional<MasterAccounts> masterAccountsOptional = masterAccountsService.findPfLoanById(id);
        return masterAccountsOptional.map(masterAccounts -> new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND));
    }

}
