package com.digi.app.erf;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.digi.app.erf.dto.CurrentDateDto;
import com.digi.app.erf.dto.ErfBlDto;
import com.digi.app.erf.dto.ErfLoanBalanceDto;
import com.digi.app.erf.dto.Erfpfdto;
import com.digi.app.erf.erfimpl.ErfDao;
import com.digi.app.erf.erfimpl.ErfService;
import com.digi.app.message.HttpResponses;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@RestController
@RequestMapping("erf")
public class ErfController {
	@Autowired
	ErfDao erfDao;
	@Autowired
	ErfService erfService;
	String databaseURL = "jdbc:ucanaccess://c://srf//ele.mdb;password=jkt@sunil.com;openExclusive=false;ignoreCase=true";
	String databaseURLeasy = "jdbc:ucanaccess://c://srf//account.mdb;password=jkt@sunil.com;openExclusive=false;ignoreCase=true";
	Connection con=null;
	@Value("${cbs.report.path}")
	private String reportRootPath;
	
	@GetMapping(path = "/erfloanlist")
	public ResponseEntity<?> erfloanlist(Principal principal) throws SQLException {
		try {
		con = DriverManager.getConnection(databaseURL);
	
			List<ErfLoanBalanceDto> lck=erfService.getLoanBalnces(con);
		 if (lck != null) {
			 return new ResponseEntity<>(lck, HttpStatus.OK);
	    } 
		}
		 catch (Exception e) {
	        System.out.println(e);
	    }finally {
			if(con!=null) {
				con.close();
			}
		}
		return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
	}
		
		@GetMapping(path = "/erfpflist")
		public ResponseEntity<?> erfpflist(Principal principal) throws SQLException {
			try {
			con = DriverManager.getConnection(databaseURL);
				List<Erfpfdto> lck=erfService.getCustomerPFBalance(con);
			 if (lck != null) {
				 return new ResponseEntity<>(lck, HttpStatus.OK);
		    } 
			}
			 catch (Exception e) {
		        System.out.println(e);
		    }finally {
				if(con!=null) {
					con.close();
				}
			}
	    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
	}
		
		@GetMapping(path = "/erfpfloanlist")
		public ResponseEntity<?> erfpfloanlist(Principal principal) throws SQLException {
			try {
			con = DriverManager.getConnection(databaseURL);
				List<Erfpfdto> lck=erfService.getCustomerPFLoanBalance(con);
			 if (lck != null) {
				 return new ResponseEntity<>(lck, HttpStatus.OK);
		    } 
			}
			 catch (Exception e) {
		        System.out.println(e);
		    }finally {
				if(con!=null) {
					con.close();
				}
			}
	    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/erfloanlist/{acno}")
	public ResponseEntity<?> branchwiselockerlist(@PathVariable String acno, Principal principal) {
		try {
			ErfLoanBalanceDto lck=erfDao.getaccountDetail(acno);
		 if (lck != null) {
			 return new ResponseEntity<>(lck, HttpStatus.OK);
	    } 
		}
		 catch (Exception e) {
	        System.out.println(e);
	    }
	    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
	}
	
	
	//--- Jasper reports
	
	
	
	
	
	
	
	@GetMapping("/printerfloans")
	private ModelAndView getprinterfloans(HttpServletRequest req,HttpServletResponse response) throws SQLException {
		ModelAndView model = new ModelAndView("showimagepdf");
		model.addObject("pagetitle", "Print PDF");
				try {
					con = DriverManager.getConnection(databaseURL);
					List<ErfLoanBalanceDto> loan=erfService.getLoanBalnces(con);
					CurrentDateDto dto=erfService.getERFDate(con);
					
					
					if(con!=null) {
						con.close();
					}
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(loan);
	JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream(reportRootPath+"erfloanlist.jrxml"));		
	System.out.println("Root path: "+reportRootPath);
	HashMap<String, Object> map=new HashMap<>();
	map.put("curdate", dto.getCndate());
		JasperPrint report=JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		
		String csvFileName=reportRootPath+"/erfloanlist.pdf";
		//String filename="Erf_loan_List.pdf";
		
		/*getExcel(report,csvFileName);*/
		getPdf(report,csvFileName);
		/*response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + filename);

		FileInputStream fileInputStream = new FileInputStream(csvFileName);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}*/
		byte[] bytesimg=Files.readAllBytes(Paths.get(csvFileName));
        String base64string=Base64.getEncoder().encodeToString(bytesimg);
        String baseconcat="data:application/pdf;base64,";
		model.addObject("file",baseconcat.concat(base64string));
		return model;
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			if(con!=null) {
				con.close();
			}
		}
		
		return model;
	}
	
	
	
	@GetMapping("/printerfpfandloans")
	private ModelAndView getprinterfpfandloans(HttpServletRequest req,HttpServletResponse response) throws SQLException {
		ModelAndView model = new ModelAndView("showimagepdf");
		model.addObject("pagetitle", "Provident fund and PF loans");
				try {
					con = DriverManager.getConnection(databaseURL);
					List<Erfpfdto> brLists=erfService.getCustomerPFBalance(con);
					List<Erfpfdto> mgmts=erfService.getCustomerPFLoanBalance(con);
					List<Erfpfdto> prems=erfService.getCustomerInsurance(con);
					brLists.forEach(brList->{
						List<Erfpfdto> filteredMgmt = mgmts.stream().filter(mgmt->{
							return mgmt.getPid()==(brList.getPid());
						}).collect(Collectors.toList());
						if(!filteredMgmt.isEmpty()) {
							Erfpfdto mgmtThis = filteredMgmt.get(0);
							brList.setPfLoanbalances(mgmtThis.getPfLoanbalances());
						}
					});
					
					brLists.forEach(brList->{
						List<Erfpfdto> filteredPrem = prems.stream().filter(prem->{
							return prem.getPid()==(brList.getPid());
						}).collect(Collectors.toList());
						if(!filteredPrem.isEmpty()) {
							Erfpfdto premThis = filteredPrem.get(0);
							brList.setInsuredAmount(premThis.getInsuredAmount());
							brList.setPremiumAmount(premThis.getPremiumAmount());
						}
					});
					
					CurrentDateDto dto=erfService.getPFDate(con);
					
					
					
					if(con!=null) {
						con.close();
					}
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(brLists);
	JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream(reportRootPath+"erfpflist.jrxml"));		
	HashMap<String, Object> map=new HashMap<>();
	map.put("curdate", dto.getCndate());
		JasperPrint report=JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		
		String csvFileName=reportRootPath+"/erfpflist.pdf";
		/*String filename="Erf_pf_List.pdf";
		
		getExcel(report,csvFileName);*/
		getPdf(report,csvFileName);
		/*response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + filename);

		FileInputStream fileInputStream = new FileInputStream(csvFileName);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}*/
		
		byte[] bytesimg=Files.readAllBytes(Paths.get(csvFileName));
        String base64string=Base64.getEncoder().encodeToString(bytesimg);
        String baseconcat="data:application/pdf;base64,";
       // System.out.println(baseconcat.concat(base64string));
		model.addObject("file",baseconcat.concat(base64string));
		return model;
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			if(con!=null) {
				con.close();
			}
		}
				return model;
		
	}
	
	
	@GetMapping("/printerfbl")
	private ModelAndView getprinterfbl(HttpServletRequest req,HttpServletResponse response) throws SQLException {
		ModelAndView model = new ModelAndView("showimagepdf");
		model.addObject("pagetitle", "Print PDfF");
				try {
					con = DriverManager.getConnection(databaseURLeasy);
					List<ErfBlDto> brLists=erfService.getBlBalance(con);
					List<ErfBlDto> mgmts=erfService.getBlBalancesum(con);
					brLists.forEach(brList->{
						List<ErfBlDto> filteredMgmt = mgmts.stream().filter(mgmt->{
							return mgmt.getBlCode().equals(brList.getBlCode());
						}).collect(Collectors.toList());
						
						if(!filteredMgmt.isEmpty()) {
							ErfBlDto mgmtThis = filteredMgmt.get(0);
							brList.setAmount(mgmtThis.getAmount());
							if(mgmtThis.getAmount()<0) {
								brList.setCreditBalance(-mgmtThis.getAmount());
							}else {
								brList.setDebitBalance(mgmtThis.getAmount());
							}
							
						}
					});
					
					CurrentDateDto dto=erfService.getEasydate(con);
					
					if(con!=null) {
						con.close();
					}
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(brLists);
	JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream(reportRootPath+"erfbl.jrxml"));		
	HashMap<String, Object> map=new HashMap<>();
	System.out.println("Current Date :"+dto.getCndate());
	map.put("curdate", dto.getCndate());
		JasperPrint report=JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		
		String csvFileName=reportRootPath+"/erfbl.pdf";
		getPdf(report,csvFileName);
		/*String filename="Erf_bl.pdf";
		
		getExcel(report,csvFileName);
		getPdf(report,csvFileName);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + filename);*/
		
		
		byte[] bytesimg=Files.readAllBytes(Paths.get(csvFileName));
        String base64string=Base64.getEncoder().encodeToString(bytesimg);
        String baseconcat="data:application/pdf;base64,";
       // System.out.println(baseconcat.concat(base64string));
		model.addObject("file",baseconcat.concat(base64string));
		
/*
		FileInputStream fileInputStream = new FileInputStream(csvFileName);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}*/
		return model;
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			if(con!=null) {
				con.close();
			}
		}
				return model;
		
	}
	
	
	@GetMapping(path = "/erfpfandloanlist")
	public ResponseEntity<?> erfpfandloanlist(Principal principal) throws SQLException {
		try {
		con = DriverManager.getConnection(databaseURL);
			List<Erfpfdto> brLists=erfService.getCustomerPFBalance(con);
			List<Erfpfdto> mgmts=erfService.getCustomerPFLoanBalance(con);
			brLists.forEach(brList->{
				List<Erfpfdto> filteredMgmt = mgmts.stream().filter(mgmt->{
					return mgmt.getPid()==(brList.getPid());
				}).collect(Collectors.toList());
				
				if(!filteredMgmt.isEmpty()) {
					Erfpfdto mgmtThis = filteredMgmt.get(0);
					brList.setPfLoanbalances(mgmtThis.getPfLoanbalances());
				}
			});
			
			
		 if (brLists != null) {
			 return new ResponseEntity<>(brLists, HttpStatus.OK);
	    } 
		}
		 catch (Exception e) {
	        System.out.println(e);
	    }finally {
			if(con!=null) {
				con.close();
			}
		}
    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
}
	
	
	
	@GetMapping(path = "/erfBlBalance")
	public ResponseEntity<?> erfBlBalance(Principal principal) throws SQLException {
		try {
		con = DriverManager.getConnection(databaseURLeasy);
			List<ErfBlDto> brLists=erfService.getBlBalance(con);
			List<ErfBlDto> mgmts=erfService.getBlBalancesum(con);
			brLists.forEach(brList->{
				List<ErfBlDto> filteredMgmt = mgmts.stream().filter(mgmt->{
					return mgmt.getBlCode().equals(brList.getBlCode());
				}).collect(Collectors.toList());
				
				if(!filteredMgmt.isEmpty()) {
					ErfBlDto mgmtThis = filteredMgmt.get(0);
					brList.setAmount(mgmtThis.getAmount());
					if(mgmtThis.getAmount()<0) {
						brList.setCreditBalance(-mgmtThis.getAmount());
					}else {
						brList.setDebitBalance(mgmtThis.getAmount());
					}
					
				}
			});
			
			
		 if (brLists != null) {
			 return new ResponseEntity<>(brLists, HttpStatus.OK);
	    } 
		}
		 catch (Exception e) {
	        System.out.println(e);
	    }finally {
			if(con!=null) {
				con.close();
			}
		}
    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
}
	
	@GetMapping(path = "/erfBlamount")
	public ResponseEntity<?> erfBlamount(Principal principal) throws SQLException {
		try {
		con = DriverManager.getConnection(databaseURLeasy);
			List<ErfBlDto> brLists=erfService.getBlBalancesum(con);
			//List<Erfpfdto> mgmts=erfService.getCustomerPFLoanBalance(con);
			/*brLists.forEach(brList->{
				List<Erfpfdto> filteredMgmt = mgmts.stream().filter(mgmt->{
					return mgmt.getPid()==(brList.getPid());
				}).collect(Collectors.toList());
				
				if(!filteredMgmt.isEmpty()) {
					Erfpfdto mgmtThis = filteredMgmt.get(0);
					brList.setPfLoanbalances(mgmtThis.getPfLoanbalances());
				}
			});*/
			
			
		 if (brLists != null) {
			 return new ResponseEntity<>(brLists, HttpStatus.OK);
	    } 
		}
		 catch (Exception e) {
	        System.out.println(e);
	    }finally {
			if(con!=null) {
				con.close();
			}
		}
    return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
}
	
	private void getExcel(JasperPrint jasperPrint, String csvFileName) {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		File outputFile = new File(csvFileName);
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
		configuration.setDetectCellType(true);//Set configuration as you like it!!
		configuration.setCollapseRowSpan(false);
		exporter.setConfiguration(configuration);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}


	private void getPdf(JasperPrint jasperPrint, String csvFileName) {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		File outputFile = new File(csvFileName);
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
		SimplePdfExporterConfiguration configuration=new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/statementsearch")
	private ModelAndView getstatementsearch() {
		ModelAndView model = new ModelAndView("customerstatement/statementsearch");
		model.addObject("pagetitle", "Statement Search");
		return model;
	}
}
