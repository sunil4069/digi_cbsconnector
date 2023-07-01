package com.digi.app.util;

import com.digi.app.adminconfig.entity.ConfigEntity;
import com.digi.app.adminconfig.service.AdminConfigService;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CommandsGenerator {
    private String username;
    private String password;
    private String fundTransferCommand;
    private String miniStatementCommand;
    private String statementCommand;
    private AdminConfigService adminConfigService;
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setAdminConfigService(AdminConfigService adminConfigService) {
        this.adminConfigService = adminConfigService;
    }

    private void setTelnetConfig() {
        Optional<ConfigEntity> configEntityOptional = adminConfigService.getConfigEntity();
        configEntityOptional.ifPresent(configEntity -> {
            this.username = configEntity.getTelnetUsername();
            this.password = tokenService.decrypt(configEntity.getTelnetPassword());
            this.fundTransferCommand = configEntity.getFundTransferCommand();
            this.miniStatementCommand = configEntity.getMiniStatementCommand();
            this.statementCommand = configEntity.getStatementCommand();
        });
    }

    public String genFundTransferTransactionCommand(FundTransferTransaction fundTransferTransaction) {
        setTelnetConfig();
        if (StringUtils.isBlank(this.fundTransferCommand)) {
            log.error("Fund transfer command not found.");
            return "FUNDS.TRANSFER,IPS.EPAYMENT/I/," + this.username + "/" + this.password + ",,DEBIT.ACCT.NO=" + fundTransferTransaction.getDrAccountNo() + ",DEBIT.CURRENCY=NPR,DEBIT.AMOUNT=" + fundTransferTransaction.getAmount() + ",DEBIT.THEIR.REF=" + fundTransferTransaction.getDrNarrative() + ",CREDIT.ACCT.NO=" + fundTransferTransaction.getCrAccountNo() + ",CREDIT.THEIR.REF=" + fundTransferTransaction.getCrNarrative() + ",COMMISSION.CODE=D,COMMISSION.TYPE=IPSCOMMTRAC,COMMISSION.AMT=NPR0,IPS.TXN.ID=" + fundTransferTransaction.getDigiTransactionId();
        } else {
            String finalCommand = this.fundTransferCommand;
            finalCommand=finalCommand.replace("<username>", this.username)
                    .replace("<password>", this.password)
                    .replace("<drAccountNumber>", fundTransferTransaction.getDrAccountNo())
                    .replace("<drAmount>", fundTransferTransaction.getAmount())
                    .replace("<drNarrative>", fundTransferTransaction.getDrNarrative())
                    .replace("<crAccountNumber>", fundTransferTransaction.getCrAccountNo())
                    .replace("<crNarrative>", fundTransferTransaction.getCrNarrative())
                    .replace("<systemTransactionId>", fundTransferTransaction.getDigiTransactionId());
            return finalCommand;
        }
    }

    public String genMiniStatementCommand(String accountNumber) {
        setTelnetConfig();
        if (StringUtils.isBlank(this.miniStatementCommand)) {
            log.error("Mini statement command not found.");
            return "ENQUIRY.SELECT,," + this.username + "/" + this.password + "/,ADBL.IPS.ACCT.MINI.STMT,ACCOUNT.NO:=" + accountNumber;
        } else {
            String finalCommand = this.miniStatementCommand;
            finalCommand=finalCommand.replace("<username>", this.username)
                    .replace("<password>", this.password)
                    .replace("<accountNumber>", accountNumber);
            return finalCommand;
        }
    }

    public String genStatementCommand(String accountNumber, String dateFrom, String dateTo) {
        setTelnetConfig();
        if (StringUtils.isBlank(this.statementCommand)) {
            log.error("Statement command not found.");
            return "ENQUIRY.SELECT,," + this.username + "/" + this.password + "/,F1.CUS.ACCT.SMT,ACCOUNT:=" + accountNumber + ",BOOKING.DATE.SEL:RG=" + dateFrom + " " + dateTo;
        } else {
            String finalCommand = this.statementCommand;
            finalCommand=finalCommand.replace("<username>", this.username)
                    .replace("<password>", this.password)
                    .replace("<accountNumber>", accountNumber)
                    .replace("<dateFrom>", dateFrom)
                    .replace("<dateTo>", dateTo);
            return finalCommand;
        }
    }


}
