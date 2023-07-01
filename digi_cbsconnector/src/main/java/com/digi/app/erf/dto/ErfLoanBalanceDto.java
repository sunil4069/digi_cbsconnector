package com.digi.app.erf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErfLoanBalanceDto {
private String acNo;
private int aCode;
private String coaAc;
private String aHead;
private int pid;
private String staffName;
private double loanBalance;
private double interestBalance;
private double penaltyInterestBalance;
private double principalSchedule;
private double interestSchedule;
private double approvedLimit;



}
