package com.digi.app.erf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erfpfdto {
	private int pid;
	private String staffName;
	private double pfBalances;
	private double pfLoanbalances;
	private double loanRatio;
	private double insuredAmount;
	private double premiumAmount;

}
