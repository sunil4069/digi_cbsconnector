package com.digi.app.erf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErfBlDto {
	private String blCode;
	private String blHead;
	private double amount;
	private double debitBalance;
	private double creditBalance;

}
