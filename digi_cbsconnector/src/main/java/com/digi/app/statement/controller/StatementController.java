package com.digi.app.statement.controller;

import com.digi.app.config.JspPathConfig;
import com.digi.app.message.HttpResponses;
import com.digi.app.message.Messages;
import com.digi.app.statement.component.StatementComponent;
import com.digi.app.statement.enums.StatementTypeEnum;
import com.digi.app.util.PageTitles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("statements")
@Slf4j
public class StatementController {
    private StatementComponent statementComponent;
    private static final String PAGE_TITLE = "pagetitle";

    @Autowired
    public void setStatementComponent(StatementComponent statementComponent) {
        this.statementComponent = statementComponent;
    }

    @GetMapping(path = "/search-form")
    public ModelAndView searchForm(StatementTypeEnum type) {
        ModelAndView modelAndView = new ModelAndView(JspPathConfig.DASHBOARD);
        switch (type) {
            case MINI_STATEMENT:
                modelAndView = new ModelAndView(JspPathConfig.MINI_STATEMENT_SEARCH);
                modelAndView.addObject(PAGE_TITLE, PageTitles.MINI_STATEMENT_SEARCH);
                break;
            case STATEMENT:
                modelAndView = new ModelAndView(JspPathConfig.STATEMENT_SEARCH);
                modelAndView.addObject(PAGE_TITLE, PageTitles.STATEMENT_SEARCH);
                break;
            default:
                log.warn("Invalid statement type provided: " + type);
        }
        return modelAndView;
    }


    /**
     * #Note: (dateFrom and dateTo are not used)
     * STEPS FOLLOWED:
     * 1. get command from StatementCommands
     * 2. send command to telnet socket component
     * 3. we get List<List<String>> as response (String converted to List<List<String>>)
     * 4. conversion List<List<String>> to List<StatementDto> from StatementComponent->setMiniStatementResponse
     * <p>
     * Example response value
     * ,BOOKING.DATE::Booking Date/Trans.ID::Trans.ID/Trans.Type::Trans Type/Cheque.No::Cheque No/Amount::Amount/Narrative::Narrative,\"27 JAN 2019 \" \"FT190278ZK9R\\BNK\" \"Outward Cheque -\" \"4 \" \"-124.00 \" \"CHECK 4 \",\"27 JAN 2019 \" \"TT19027ZLKGN\\BNK\" \"Transfer Debit \" \"3 \" \"-122.00 \" \"CHEQUE CHECK 3 \",\"27 JAN 2019 \" \"TT190270FD94\\BNK\" \"Deposit Cash(Loc\" \"5 \" \"125.00 \" \"O/W Clearing ECC\",\"27 JAN 2019 \" \"TT19027DMNMR\\BNK\" \"Cash Withdrawal(\" \"2 \" \"-121.00 \" \"CHEQUE CHECK 2 \",\"27 JAN 2019 \" \"TT190270HK18\\BNK\" \"Cash Withdrawal(\" \"1 \" \"-120.00 \" \"CHEQUE CHECK \",\"27 JAN 2019 \" \"FT19027D1KFK \" \"Transfer IPS Cre\" \" \" \"1,250.00 \" \"TESTCR \",\"27 JAN 2019 \" \"FT19027109DN \" \"Transfer IPS Cre\" \" \" \"1,251.00 \" \"TESTCR \",\"CURRENT BALANCE \" \" \" \" \" \" \" \"156,303.81 \" \" \"
     */
    @GetMapping(value = "/telnetStatement")
    public ResponseEntity<Messages> searchResult(String accountNumber, String dateFrom, String dateTo, StatementTypeEnum statementTypeEnum, Principal principal) {
        ResponseEntity<Messages> messagesResponseEntity;
        if (StringUtils.isBlank(accountNumber)) {
            messagesResponseEntity = new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
        } else {
            switch (statementTypeEnum) {
                case STATEMENT:
                    messagesResponseEntity = statementComponent.getStatementSearchResult(principal.getName(), accountNumber, dateFrom, dateTo);
                    break;
                case MINI_STATEMENT:
                    messagesResponseEntity = statementComponent.getMiniStatementSearchResult(principal.getName(), accountNumber);
                    break;
                default:
                    messagesResponseEntity = new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
            }
        }
        return messagesResponseEntity;
    }
}
