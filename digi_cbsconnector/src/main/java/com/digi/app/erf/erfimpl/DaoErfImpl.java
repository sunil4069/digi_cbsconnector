package com.digi.app.erf.erfimpl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.digi.app.erf.dto.ErfLoanBalanceDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DaoErfImpl implements ErfDao{
	private NamedParameterJdbcTemplate template;
	DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
String erfDbase="erfdata.";
	@Override
	public List<ErfLoanBalanceDto> getErfLoaList() {
		String query = "Select AcNo as acNo,ABal as loanBalance,IntBal+NInterest+LastTMonth as interestBalance,"
				+ "ABal*PenalDays*PenalRate/36500 as penaltyInterestBalance from "+erfDbase+"vgettodayacdetail";
		return (ArrayList<ErfLoanBalanceDto>) template.query(query,
				new BeanPropertyRowMapper<ErfLoanBalanceDto>(ErfLoanBalanceDto.class));
	}
	
	
	
	
	@Override
	public ErfLoanBalanceDto getaccountDetail(String acno) {
		try {
		String query = "select c.PID as pid,c.PName as  staffName,a.AcNo as acNo,a.ACode as aCode,b.AHead as aHead,b.COAAC as coaAc FROM "+erfDbase+"tbl3personalinfo c join "+erfDbase+"tbl4personalaccounts a on c.PID=a.PID" + 
				"  join "+erfDbase+"tbl2accode b on a.ACode=b.ACode where a.AcNo='"+acno+"'";
		return template.queryForObject(query, new BeanPropertySqlParameterSource(ErfLoanBalanceDto.class),
				new BeanPropertyRowMapper<ErfLoanBalanceDto>(ErfLoanBalanceDto.class));
} catch (Exception e) {
	System.out.println(e);
}
return null;

	}
	
	@Override
	public ErfLoanBalanceDto getLoanLimit(String acno) {
		try {
		String query = "select AcNo as acNo,AppAmt as approvedLimit FROM "+erfDbase+"tblloandetail where AcNo='"+acno+"'";
		return template.queryForObject(query, new BeanPropertySqlParameterSource(ErfLoanBalanceDto.class),
				new BeanPropertyRowMapper<ErfLoanBalanceDto>(ErfLoanBalanceDto.class));
} catch (Exception e) {
	System.out.println(e);
}
return null;

	}
	
	
	@Override
	public ErfLoanBalanceDto getaccountDetailSchedule(String acno) {
		try {
		String query = "select acno as acNo,sum(prdr-prcr)  as principalSchedule,sum(indr-incr)  as interestSchedule from "
				+erfDbase+"paymentcurdue group by acno having acno='"+acno+"'";
		return template.queryForObject(query, new BeanPropertySqlParameterSource(ErfLoanBalanceDto.class),
				new BeanPropertyRowMapper<ErfLoanBalanceDto>(ErfLoanBalanceDto.class));
} catch (EmptyResultDataAccessException e) {
	System.out.println("Welcome");
}
return null;

	}
	
	
	
}
