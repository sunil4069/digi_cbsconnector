package com.digi.app.erf.erfimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.digi.app.erf.dto.CurrentDateDto;
import com.digi.app.erf.dto.ErfBlDto;
import com.digi.app.erf.dto.ErfLoanBalanceDto;
import com.digi.app.erf.dto.Erfpfdto;

@Service
public class ErfService {
	@Autowired
	ErfDao erfDao;
	public List<ErfLoanBalanceDto> getLoanBalnces(Connection con) {
		ArrayList<ErfLoanBalanceDto> erfBalanceList=new ArrayList<>();
		List<ErfLoanBalanceDto> erfLoan=erfDao.getErfLoaList();
		for(ErfLoanBalanceDto erf:erfLoan) {
			ErfLoanBalanceDto dto=new ErfLoanBalanceDto();
			ErfLoanBalanceDto erfd=erfDao.getaccountDetail(erf.getAcNo());
			dto.setAcNo(erf.getAcNo());
			dto.setPid(erfd.getPid());
		//	ErfLoanBalanceDto cus=this.getCustomerDetail(con,erfd.getPid());
		//	dto.setStaffName(cus.getStaffName());
			dto.setStaffName(erfd.getStaffName());
			dto.setACode(erfd.getACode());
			dto.setAHead(erfd.getAHead());
			dto.setCoaAc(erfd.getCoaAc());
			dto.setLoanBalance(erf.getLoanBalance());
			if(erfd.getACode()==25102) {
				dto.setInterestBalance(erf.getInterestBalance());
				dto.setPenaltyInterestBalance(erf.getPenaltyInterestBalance());
				}else {
					dto.setInterestBalance(0);
					dto.setPenaltyInterestBalance(0);	
				}
			if(erfd.getACode()!=25102) {
			ErfLoanBalanceDto sche=erfDao.getaccountDetailSchedule(erf.getAcNo());
			try {
			
			if(sche!=null) {
			dto.setPrincipalSchedule(sche.getPrincipalSchedule());
			dto.setInterestBalance(sche.getInterestSchedule());
			}else {
				dto.setPrincipalSchedule(0);
				dto.setInterestBalance(0);
			}
			ErfLoanBalanceDto erfapproved=erfDao.getLoanLimit(erf.getAcNo());
			dto.setApprovedLimit(erfapproved.getApprovedLimit());
			}catch (NullPointerException e) {
				System.out.println("Welcome 2");
			}
			
			/*List<ErfLoanBalanceDto> erfpf=this.getCustomerPFBalance(con);
			for(ErfLoanBalanceDto erfpfd:erfpf) {
				ErfLoanBalanceDto x=new ErfLoanBalanceDto();
				x.setAcNo(erfpfd.getAcNo());
				x.setPid(erfpfd.getPid());
				x.setACode(erfpfd.getACode());
				x.setAHead(erfpfd.getAHead());
				x.setPName(erfpfd.getPName());
				x.setLoanBalance(erfpfd.getLoanBalance());
				erfBalanceList.add(x);
			}*/
			
			
			}
			erfBalanceList.add(dto);
			
			
		}
		return erfBalanceList;
	}
	
	
	
	public ErfLoanBalanceDto getCustomerDetail(Connection con,int pid){
		 ErfLoanBalanceDto cus=new ErfLoanBalanceDto();
        try {
             String sql="";
            sql = "select PID,PName from Tbl3PersonalInfo where PID="+pid;
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
            	cus.setStaffName(result.getString("PName"));
            }else {
            	cus.setStaffName("");
            }
             return cus;
        } catch (Exception e) {
			System.out.println(e);
		}
        return cus;
    }
	
	
	public List<Erfpfdto> getCustomerPFBalance(Connection con){
		ArrayList<Erfpfdto> pf=new ArrayList<>();
		/*LocalTime timeStart=LocalTime.now();
		System.out.println(timeStart);*/
		try {
             String sql="";
            sql = "select PID,PName,TBal from QryPBal where ACode=11101";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            
            while (result.next()) {
            	Erfpfdto cus=new Erfpfdto();
            	cus.setPid(result.getInt("PID"));
            	cus.setStaffName(result.getString("PName"));
            	cus.setPfBalances(-result.getDouble("TBal"));
            	pf.add(cus);
            }
             return pf;
             
        } catch (Exception e) {
			System.out.println(e);
		}
        return pf;
    }
	
	
	public List<Erfpfdto> getCustomerPFLoanBalance(Connection con){
		ArrayList<Erfpfdto> pf=new ArrayList<>();
		try {
             String sql="";
            sql = "select PID,PName,TBal from QryPBal where ACode=25101";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            
            while (result.next()) {
            	Erfpfdto cus=new Erfpfdto();
            	cus.setPid(result.getInt("PID"));
            	cus.setStaffName(result.getString("PName"));
            	cus.setPfLoanbalances(result.getDouble("TBal"));
            	pf.add(cus);
            }
             return pf;
             
        } catch (Exception e) {
			System.out.println(e);
		}
        return pf;
    }
	
	
	
	public List<Erfpfdto> getCustomerInsurance(Connection con){
		ArrayList<Erfpfdto> pf=new ArrayList<>();
		try {
             String sql="";
            sql = "select staffCode as PID,sum(InsuredAmt) as insuredAmount,sum(AnnualPremium) as premiumAmount from insPremium group by staffCode";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            
            while (result.next()) {
            	Erfpfdto cus=new Erfpfdto();
            	cus.setPid(result.getInt("PID"));
            	cus.setInsuredAmount(result.getDouble("insuredAmount"));
            	cus.setPremiumAmount(result.getDouble("premiumAmount"));
            	pf.add(cus);
            }
             return pf;
             
        } catch (Exception e) {
			System.out.println(e);
		}
        return pf;
    }
	
	
	public List<ErfBlDto> getBlBalance(Connection con){
		ArrayList<ErfBlDto> pf=new ArrayList<>();
		/*LocalTime timeStart=LocalTime.now();
		System.out.println(timeStart);*/
		try {
             String sql="";
            sql = "select BLCODE,BLHEAD from tblBlAc";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            
            while (result.next()) {
            	ErfBlDto cus=new ErfBlDto();
            	cus.setBlCode(result.getString("BLCODE"));
            	cus.setBlHead(result.getString("BLHEAD"));
            	pf.add(cus);
            }
             return pf;
             
        } catch (Exception e) {
			System.out.println(e);
		}
        return pf;
    }
	
	
	public List<ErfBlDto> getBlBalancesum(Connection con){
		ArrayList<ErfBlDto> pf=new ArrayList<>();
		/*LocalTime timeStart=LocalTime.now();
		System.out.println(timeStart);*/
		try {
             String sql="";
            sql = "select a.blCode,sum(t.Dr-t.cr)  as amount from tblAc a join TblTrans t on a.AcCode=t.AcCode"
            		+ " group by a.blCode";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            
            while (result.next()) {
            	ErfBlDto cus=new ErfBlDto();
            	cus.setBlCode(result.getString("BLCODE"));
            	cus.setAmount(result.getDouble("amount"));
            	pf.add(cus);
            }
             return pf;
             
        } catch (Exception e) {
			System.out.println(e);
		}
        return pf;
    }
	
	
	
	
	public Erfpfdto getPFLoan(Connection con,int pid){
		Erfpfdto cus=new Erfpfdto();
        try {
             String sql="";
             
            sql = "select PID,TBal from QryPBal where ACode=25101 and pid="+pid +" limit 1";;
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
           if (result.next()) {
            	cus.setPfLoanbalances(result.getInt("TBal"));
            }else {
            	cus.setPfLoanbalances(0);
            }
             return cus;
        } catch (Exception e) {
			System.out.println(e);
		}
        return cus;
    }
	
	public CurrentDateDto getERFDate(Connection con) {
		CurrentDateDto dto=new CurrentDateDto();
		try {
            String sql="";
            
           sql = "select CDate,CNDate from TblCdate limit 1";;
           Statement statement = con.createStatement();
           ResultSet result = statement.executeQuery(sql);
          if (result.next()) {
           	dto.setCdate(result.getDate("CDate"));
           	dto.setCndate(result.getString("CNDate"));
           }
            return dto;
       } catch (Exception e) {
			System.out.println(e);
		}
		return dto;
	}
	
	public CurrentDateDto getEasydate(Connection con) {
		CurrentDateDto dto=new CurrentDateDto();
		try {
            String sql="";
            
           sql = "select max(TrDate) as TrDate from TblVoucherDetail";;
           Statement statement = con.createStatement();
           ResultSet result = statement.executeQuery(sql);
          if (result.next()) {
           	dto.setCndate(result.getString("TrDate"));
           }
            return dto;
       } catch (Exception e) {
			System.out.println(e);
		}
		return dto;
		
		
	}
	
	public CurrentDateDto getPFDate(Connection con) {
		CurrentDateDto dto=new CurrentDateDto();
		try {
            String sql="";
            
           sql = "select CDate,CNDate from TblCdate limit 1";;
           Statement statement = con.createStatement();
           ResultSet result = statement.executeQuery(sql);
          if (result.next()) {
           	dto.setCdate(result.getDate("CDate"));
           	dto.setCndate(result.getString("CNDate"));
           }
            return dto;
       } catch (Exception e) {
			System.out.println(e);
		}
		return dto;
	}
	
	public CurrentDateDto getAllinOne(Connection con) {
		CurrentDateDto dto=new CurrentDateDto();
		try {
            String sql="";
            
           sql = "select CDate,CNDate from TblCdate limit 1";;
           Statement statement = con.createStatement();
           ResultSet result = statement.executeQuery(sql);
          if (result.next()) {
           	dto.setCdate(result.getDate("CDate"));
           	dto.setCndate(result.getString("CNDate"));
           }
            return dto;
       } catch (Exception e) {
			System.out.println(e);
		}
		return dto;
		
		
	}
	
	

}
