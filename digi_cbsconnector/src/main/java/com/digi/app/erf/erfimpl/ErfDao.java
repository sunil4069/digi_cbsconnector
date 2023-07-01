package com.digi.app.erf.erfimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digi.app.erf.dto.ErfLoanBalanceDto;
@Service
public interface ErfDao {
	public List<ErfLoanBalanceDto> getErfLoaList();
	public ErfLoanBalanceDto getaccountDetailSchedule(String acno);
	public ErfLoanBalanceDto getaccountDetail(String acno);
	public ErfLoanBalanceDto getLoanLimit(String acno);
}
