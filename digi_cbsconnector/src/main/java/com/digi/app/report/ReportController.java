package com.digi.app.report;

import com.digi.app.component.JasperReportExportComponent;
import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.exception.ProductNotfoundException;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.fundtransfer.service.FundTransferTransactionService;
import com.digi.app.message.HttpResponses;
import com.digi.app.statement.component.StatementComponent;
import com.digi.app.statement.dto.StatementDto;
import com.digi.app.statement.enums.StatementTypeEnum;
import com.digi.app.telnet.component.TelnetConnectionComponent;
import com.digi.app.util.CommandsGenerator;
import com.digi.app.util.UtilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("reports")
public class ReportController {
	@Value("${cbs.report.path}")
	private String reportRootPath;
	
    private ReportService reportService;
    private UtilitiesService utilitiesService;
    private JasperReportExportComponent jasperReportExportComponent;
    private FundTransferTransactionService fundTransferTransactionService;
    private StatementComponent statementComponent;
    private CommandsGenerator commandsGenerator;
    private TelnetConnectionComponent telnetConnectionComponent;

    @Autowired
    public void setTelnetConnectionComponent(TelnetConnectionComponent telnetConnectionComponent) {
        this.telnetConnectionComponent = telnetConnectionComponent;
    }

    @Autowired
    public void setCommandsGenerator(CommandsGenerator commandsGenerator) {
        this.commandsGenerator = commandsGenerator;
    }

    @Autowired
    public void setFundTransferTransactionService(FundTransferTransactionService fundTransferTransactionService) {
        this.fundTransferTransactionService = fundTransferTransactionService;
    }

    @Autowired
    public void setStatementComponent(StatementComponent statementComponent) {
        this.statementComponent = statementComponent;
    }

    @Autowired
    public void setJasperReportExportComponent(JasperReportExportComponent jasperReportExportComponent) {
        this.jasperReportExportComponent = jasperReportExportComponent;
    }

    @Autowired
    public void setUtilitiesService(UtilitiesService utilitiesService) {
        this.utilitiesService = utilitiesService;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

	@GetMapping(value = "/jaspers/transactions")
	public void getTransactionJasperReport(HttpServletRequest request, HttpServletResponse response) {
		/*String filename = "transaction";*/
		String date1=request.getParameter("dateFrom");
		String date2=request.getParameter("dateTo");
		String filename = "transaction";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date1", date1);
		param.put("date2", date2);
		
		String report = reportRootPath + filename + ".jrxml";
		
		jasperReportExportComponent.exportJrxmlReport(report, param, response);
	}
	
	@GetMapping(value = "/jaspers/vouchertransactions")
	public void getVoucherJasperReport(HttpServletRequest request, HttpServletResponse response) {
		/*String filename = "transaction";*/
		String date1=request.getParameter("dateFrom");
		//String date2=request.getParameter("dateTo");
		String filename = "voucherdaily";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trdate", date1);
		//param.put("date2", date2);
		
		String report = reportRootPath + filename + ".jrxml";
		
		jasperReportExportComponent.exportJrxmlReport(report, param, response);
	}
	


    @GetMapping("transactions/search")
    public ModelAndView transactionSearchForm() {
        ModelAndView modelAndView = new ModelAndView("reports/transactions");
        return modelAndView;
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity<?> getTransactions(String dateFrom, String dateTo) {
        List<FundTransferReport> fundTransferReportList = new ArrayList<>();
        List<FundTransferTransaction> fundTransferTransactions = reportService.getAuthorizedTransactions(dateFrom,
                dateTo);
        fundTransferTransactions.forEach(fundTransferTransaction -> {
            FundTransferReport fundTransferReport = new FundTransferReport();
            try {
                BeanUtils.copyProperties(fundTransferReport, fundTransferTransaction);
            } catch (Exception e) {
                log.error("Fund Transfer transaction report error\n" + e);
            }
            fundTransferReportList.add(fundTransferReport);
        });
        if (fundTransferReportList.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpResponses.fetched(fundTransferReportList), HttpStatus.OK);
        }
    }


    @GetMapping(path = "/voucher")
    public ModelAndView getvoucherPage(String digiTransactionId) {
    	
        ModelAndView modelAndView = new ModelAndView("transaction/voucher");
        modelAndView.addObject("pagetitle", "Print Voucher");
        Optional<FundTransferTransaction> ftvoucher = fundTransferTransactionService.findByDigiTransactionId(digiTransactionId);
        if (ftvoucher.isPresent()) {
            FundTransferTransaction voucher = ftvoucher.get();
            modelAndView.addObject("voucher", voucher);
        }
        String vid1 = ftvoucher.get().getLoanAccountNumber();

        String vid = vid1.substring(0, 5);
        String bankac = "1281000020202";
        String drac = "180101";

        if (vid.equals("25101")) {
            drac = "1806";
        }
        modelAndView.addObject("drac", drac);


        return modelAndView;
    }


    @GetMapping(value = "/transactions/today")
    public ResponseEntity<?> getTodayTransactions() {
        String currentDate = utilitiesService.getCurrentDateInDigiDateFormat();
        return getTransactions(currentDate, currentDate);
    }

    @GetMapping("/statements/downloads")
    public final void getStatementsCsv(String accountNumber, String dateFrom, String dateTo, StatementTypeEnum statementTypeEnum, HttpServletResponse responses, Principal principal) {
        if (StringUtils.isBlank(accountNumber)) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.ACCOUNT_CANNOT_BE_EMPTY));
        } else {
            String command = StringUtils.EMPTY;
            List<StatementDto> responseDtoList = new ArrayList<>();
            switch (statementTypeEnum) {
                case STATEMENT:
                    command = commandsGenerator.genStatementCommand(accountNumber, dateFrom, dateTo);
                    responseDtoList = (List<StatementDto>) telnetConnectionComponent.telnetConnectionAndResponseList(principal.getName(), command, StatementTypeEnum.STATEMENT);
                    break;
                case MINI_STATEMENT:
                    command = commandsGenerator.genMiniStatementCommand(accountNumber);
                    responseDtoList = (List<StatementDto>) telnetConnectionComponent.telnetConnectionAndResponseList(principal.getName(), command, StatementTypeEnum.STATEMENT);
                    break;
                default:
                    throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.CSV_FILE_GENERATION_EXCEPTION_MESSAGE));
            }
            if (!responseDtoList.isEmpty()) {
                List<StatementReportDto> statementReportDtoList = new ArrayList<>();
                responseDtoList.forEach(statementDto -> {
                    StatementReportDto statementReportDto = new StatementReportDto();
                    try {
                        BeanUtils.copyProperties(statementReportDto, statementDto);
                        statementReportDtoList.add(statementReportDto);
                    } catch (Exception e) {
                        log.error("Statement to report copy properties exception\n" + e);
                    }
                });
                if(!statementReportDtoList.isEmpty()){
                    String fileName = "adbl_statement_" + (new Date()).toString();
                    CsvGenerator.generateCsv(statementReportDtoList, fileName, responses);
                }
            }
            else{
                throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.NO_RESULTS));
            }
        }
    }

    @GetMapping(value = "/transactions/downloads")
    public final void getTransactionsCsv(String dateFrom, String dateTo, HttpServletResponse responses) {
        try {
            List<FundTransferReport> fundTransferReportList = new ArrayList<>();
            List<FundTransferTransaction> fundTransferTransactions = reportService.getAuthorizedTransactions(dateFrom,
                    dateTo);
            fundTransferTransactions.forEach(fundTransferTransaction -> {
                FundTransferReport fundTransferReport = new FundTransferReport();
                try {
                    BeanUtils.copyProperties(fundTransferReport, fundTransferTransaction);
                } catch (Exception e) {
                    System.out.println(e);
                }
                fundTransferReportList.add(fundTransferReport);
            });
            String fileName = "adbl_transactions_" + (new Date()).toString();
            CsvGenerator.generateCsv(fundTransferReportList, fileName, responses);
        } catch (Exception e) {
            log.error("Report generation exception..." + e);
            throw new ProductNotfoundException();
        }
    }


    }
