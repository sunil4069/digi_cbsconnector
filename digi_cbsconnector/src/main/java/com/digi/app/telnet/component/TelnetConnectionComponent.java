package com.digi.app.telnet.component;

import com.digi.app.adminconfig.entity.ConfigEntity;
import com.digi.app.adminconfig.service.AdminConfigService;
import com.digi.app.enums.UserActionTypeEnum;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.service.LogService;
import com.digi.app.statement.dto.MiniStatementDto;
import com.digi.app.statement.dto.StatementDto;
import com.digi.app.statement.enums.StatementTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

@Component
@Slf4j
public class TelnetConnectionComponent {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private String ip;
    private int port;
    private AdminConfigService adminConfigService;
    private LogService logService;

    @Autowired
    public void setAdminConfigService(AdminConfigService adminConfigService) {
        this.adminConfigService = adminConfigService;
    }

    @Autowired
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    private void setTelnetConfig() {
        Optional<ConfigEntity> configEntityOptional = adminConfigService.getConfigEntity();
        configEntityOptional.ifPresent(configEntity -> {
            this.ip = configEntity.getTelnetIp();
            this.port = configEntity.getTelnetPort();
        });
    }

    public List<?> telnetConnectionAndResponseList(String username, String command, StatementTypeEnum statementType) {
        setTelnetConfig();
        List<MiniStatementDto> miniStatementDtoList = new ArrayList<>();
        List<StatementDto> statementDtoList = new ArrayList<>();
        boolean connectionStatus = startConnection(this.ip, this.port);
        if (connectionStatus) {
            String response = sendMessage(command);
            logService.saveUserActionLog(username, command, response, String.valueOf(UserActionTypeEnum.STATEMENT_SEARCH)); //save log
            switch (statementType) {
                case MINI_STATEMENT:
//                    response = ",BOOKING.DATE::Booking Date/Trans.ID::Trans.ID/Trans.Type::Trans Type/Cheque.No::Cheque No/Amount::Amount/Narrative::Narrative,\\\"27 JAN 2019 \\\" \\\"FT190278ZK9R\\\\BNK\\\" \\\"Outward Cheque -\\\" \\\"4 \\\" \\\"-124.00 \\\" \\\"CHECK 4 \\\",\\\"27 JAN 2019 \\\" \\\"TT19027ZLKGN\\\\BNK\\\" \\\"Transfer Debit \\\" \\\"3 \\\" \\\"-122.00 \\\" \\\"CHEQUE CHECK 3 \\\",\\\"27 JAN 2019 \\\" \\\"TT190270FD94\\\\BNK\\\" \\\"Deposit Cash(Loc\\\" \\\"5 \\\" \\\"125.00 \\\" \\\"O/W Clearing ECC\\\",\\\"27 JAN 2019 \\\" \\\"TT19027DMNMR\\\\BNK\\\" \\\"Cash Withdrawal(\\\" \\\"2 \\\" \\\"-121.00 \\\" \\\"CHEQUE CHECK 2 \\\",\\\"27 JAN 2019 \\\" \\\"TT190270HK18\\\\BNK\\\" \\\"Cash Withdrawal(\\\" \\\"1 \\\" \\\"-120.00 \\\" \\\"CHEQUE CHECK \\\",\\\"27 JAN 2019 \\\" \\\"FT19027D1KFK \\\" \\\"Transfer IPS Cre\\\" \\\" \\\" \\\"1,250.00 \\\" \\\"TESTCR \\\",\\\"27 JAN 2019 \\\" \\\"FT19027109DN \\\" \\\"Transfer IPS Cre\\\" \\\" \\\" \\\"1,251.00 \\\" \\\"TESTCR \\\",\\\"CURRENT BALANCE \\\" \\\" \\\" \\\" \\\" \\\" \\\" \\\"156,303.81 \\\" \\\" \\\"";
                    miniStatementDtoList = parseMiniStatementResponse(response);
                    break;
                case STATEMENT:
//                    response = ",S.NO::S.NO/NEP.VDT::Nepali Value Date/VAL.DT::Value Date/NP.BDT::Nepali Booking Date/B.DT::Booking Date/T.REF::Trans Ref/NARR::Narrative/TXN.TYPE::Txn Type/CR.::CREDIT/OP.BAL::Balance/TOT.TOVR::Total Turnover,\"                \"    \"\"      \"\"      \"\"      \"\"      \"\"      \"Balance at Period Start\"       \"\"      \"\"      \"\"      \"160,000.00\"    \"\",\"               1\"   \"20750919\"      \"20190103\"      \"20750919\"      \"20190103\"      \"FT19003HF1D1\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"155,000.00\"    \"\",\"               2\"   \"20750923\"      \"20190107\"      \"20750923\"      \"20190107\"      \"FT190079LSRH\"  \"UMHL 5- CASH DI\"       \"Transfer IPS Credit\"   \"0.00\"  \"536.75\"        \"155,536.75\"    \"\",\"               3\"   \"20750923\"      \"20190107\"      \"20750923\"      \"20190107\"      \"FT190079LSRH\"  \"AC-0100200000009012\"   \"IPS Debit Charge\"      \"-10.00\"        \"0.00\"  \"155,526.75\"    \"\",\"               4\"   \"20750924\"      \"20190108\"      \"20750924\"      \"20190108\"      \"FT19008WY88D\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"150,526.75\"    \"\",\"               5\"   \"20750924\"      \"20190108\"      \"20750924\"      \"20190108\"      \"TT19008JF0C9\\F07\"      \"SHIVA AMATYA\"  \"Cash Withdrawal(Local Ccy)\"    \"-20,000.00\"    \"0.00\"  \"130,526.75\"    \"\",\"               6\"   \"20750927\"      \"20190111\"      \"20750927\"      \"20190111\"      \"FT190114GWDQ\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-5,000.00\"     \"0.00\"  \"125,526.75\"    \"\",\"               7\"   \"20750927\"      \"20190111\"      \"20750927\"      \"20190111\"      \"FT19011DC1K0\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-500.00\"       \"0.00\"  \"125,026.75\"    \"\",\"               8\"   \"20751001\"      \"20190115\"      \"20750930\"      \"20190114\"      \"0100200000009012-20190114\"     \"\"      \"Credit Interest\"       \"0.00\"  \"2,304.40\"      \"127,331.15\"    \"\",\"               9\"   \"20751001\"      \"20190115\"      \"20750930\"      \"20190114\"      \"0100200000009012-20190114\"     \"\"      \"Tax Amount Due\"        \"-115.22\"       \"0.00\"  \"127,215.93\"    \"\",\"              10\"   \"20751003\"      \"20190117\"      \"20751003\"      \"20190117\"      \"FT19017JH8W2\\BNK\"      \"145000\"        \"Internet Bank Payment\" \"0.00\"  \"17,784.07\"     \"145,000.00\"    \"\",\"              11\"   \"20751003\"      \"20190117\"      \"20751003\"      \"20190117\"      \"FT19017GVRKH\\BNK\"      \"164000\"        \"Internet Bank Payment\" \"0.00\"  \"19,000.00\"     \"164,000.00\"    \"\",\"              12\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018TM9HB\\BNK\"      \"NT Prepaid Topup\"      \"Mobile payment\"        \"-10.00\"        \"0.00\"  \"163,990.00\"    \"\",\"              13\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190186LXPX\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-5.00\" \"0.00\"  \"163,985.00\"    \"\",\"              14\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190189C3JY\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-5.00\" \"0.00\"  \"163,980.00\"    \"\",\"              15\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018DYQ8C\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,979.00\"    \"\",\"              16\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018WYJ9C\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,978.00\"    \"\",\"              17\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018L1PSQ\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,977.00\"    \"\",\"              18\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018F1DVK\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,976.00\"    \"\",\"              19\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018T6V92\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,975.00\"    \"\",\"              20\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018TC647\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,974.00\"    \"\",\"              21\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190188V6CT\\BNK\"      \"NT Prepaid Topup\"      \"Mobile payment\"        \"-20.00\"        \"0.00\"  \"163,954.00\"    \"\",\"              22\"   \"20751005\"      \"20190119\"      \"20751005\"      \"20190119\"      \"FT19019HB68B\\BNK\"      \"164\"   \"Mobile payment\"        \"0.00\"  \"46.00\" \"164,000.00\"    \"\",\"              23\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT190207NV81\\BNK\"      \"test v5\"       \"Mobile payment\"        \"0.00\"  \"10.00\" \"164,010.00\"    \"\",\"              24\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020V1Z34\\BNK\"      \"Ncell Topup\"   \"Mobile payment\"        \"-10.00\"        \"0.00\"  \"164,000.00\"    \"\",\"              25\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020R98V3\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-15,000.00\"    \"0.00\"  \"149,000.00\"    \"\",\"              26\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020NMCJ6\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"10.00\" \"149,010.00\"    \"\",\"              27\"   \"20751007\"      \"20190121\"      \"20751007\"      \"20190121\"      \"FT1902152G20\"  \"NMB 7475-011282\"       \"Transfer IPS Credit\"   \"0.00\"  \"8,325.00\"      \"157,335.00\"    \"\",\"              28\"   \"20751007\"      \"20190121\"      \"20751007\"      \"20190121\"      \"FT1902152G20\"  \"AC-0100200000009012\"   \"IPS Debit Charge\"      \"-20.00\"        \"0.00\"  \"157,315.00\"    \"\",\"              29\"   \"20751008\"      \"20190122\"      \"20751008\"      \"20190122\"      \"TT190220RFNF\\F17\"      \"SANTOSH DEO\"   \"Cash Withdrawal(Local Ccy)\"    \"-40,000.00\"    \"0.00\"  \"117,315.00\"    \"\",\"              30\"   \"20751010\"      \"20190124\"      \"20751010\"      \"20190124\"      \"FT19024YWZSW\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"112,315.00\"    \"\",\"              31\"   \"20751010\"      \"20190124\"      \"20751010\"      \"20190124\"      \"TT190243YDLP\\G18\"      \"KRISHNA PRASAD PAUDEL\" \"Deposit Cash(Local Currency)\"  \"0.00\"  \"50,000.00\"     \"162,315.00\"    \"\",\"              32\"   \"20751013\"      \"20190127\"      \"20751013\"      \"20190127\"      \"FT19027BR3CS\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"10.00\" \"162,325.00\"    \"\",\"              33\"   \"20751013\"      \"20190127\"      \"20751013\"      \"20190127\"      \"FT19027L3XYC\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"100.00\"        \"162,425.00\"    \"\",\"              34\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028ZFXLV\\BNK\"      \"166000\"        \"Internet Bank Payment\" \"0.00\"  \"3,575.00\"      \"166,000.00\"    \"\",\"              35\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028WCXVW\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-10,000.00\"    \"0.00\"  \"156,000.00\"    \"\",\"              36\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028X61MD\\BNK\"      \"Test for 159\"  \"Mobile payment\"        \"0.00\"  \"3,000.00\"      \"159,000.00\"    \"\",\"              37\"   \"20751015\"      \"20190129\"      \"20751015\"      \"20190129\"      \"FT190292R2L2\\BNK\"      \"TSTTRF\"        \"Mobile payment\"        \"0.00\"  \"100.00\"        \"159,100.00\"    \"\",\"              38\"   \"20751015\"      \"20190129\"      \"20751015\"      \"20190129\"      \"FT190292RQ9J\\BNK\"      \"TestQR\"        \"Mobile payment\"        \"-100.00\"       \"0.00\"  \"159,000.00\"    \"\",\"              39\"   \"20751016\"      \"20190130\"      \"20751016\"      \"20190130\"      \"FT190301YCMP\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"154,000.00\"    \"\",\"              40\"   \"20751018\"      \"20190201\"      \"20751018\"      \"20190201\"      \"FT19032VDP73\\BNK\"      \"9847020585\"    \"eSewa Payment\" \"-5,000.00\"     \"0.00\"  \"149,000.00\"    \"\",\"              41\"   \"20751018\"      \"20190201\"      \"20751018\"      \"20190201\"      \"FT19032FGF3L\\BNK\"      \"9847020585\"    \"eSewa Payment\" \"-2,000.00\"     \"0.00\"  \"147,000.00\"    \"\",\"              42\"   \"20751020\"      \"20190203\"      \"20751020\"      \"20190203\"      \"FT19034M43WQ\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-4,000.00\"     \"0.00\"  \"143,000.00\"    \"\",\"              43\"   \"20751020\"      \"20190203\"      \"20751020\"      \"20190203\"      \"FT19034X19BW\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-2,000.00\"     \"0.00\"  \"141,000.00\"    \"\",\"              44\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT19035GDH9R\\BNK\"      \"41745\" \"Fonepay Payment\"       \"-829.00\"       \"0.00\"  \"140,171.00\"    \"\",\"              45\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT190354RWSG\\BNK\"      \"Pankaj 2 Santosh\"      \"Internet Bank Payment\" \"-5,000.00\"     \"0.00\"  \"135,171.00\"    \"\",\"              46\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT19035KBLN0\\BNK\"      \"ASBAALBSL1301310\"      \"Transfer Out\"  \"-10.00\"        \"0.00\"  \"135,161.00\"    \"\",\"              47\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"SB.0100200000009012\\BNK\"       \"\"      \"Cheque Issued Commission Dr\"   \"-950.00\"       \"0.00\"  \"134,211.00\"    \"\",\"              48\"   \"20751022\"      \"20190205\"      \"20751022\"      \"20190205\"      \"FT19036GLZLN\\BNK\"      \"Rev 00000009012\"       \"Transfer In\"   \"0.00\"  \"950.00\"        \"135,161.00\"    \"\",\"              49\"   \"20751022\"      \"20190205\"      \"20751022\"      \"20190205\"      \"FT19036C57LV\\BNK\"      \"\"      \"POS TRANSACTION\"       \"-3,809.98\"     \"0.00\"  \"131,351.02\"    \"\",\"              50\"   \"20751023\"      \"20190206\"      \"20751023\"      \"20190206\"      \"FT1903792XHH\"  \"PANKAJ\"        \"Transfer In\"   \"0.00\"  \"371,000.00\"    \"502,351.02\"    \"\",\"              51\"   \"20751023\"      \"20190206\"      \"20751023\"      \"20190206\"      \"FT1903784CG8\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-2,000.00\"     \"0.00\"  \"500,351.02\"    \"\",\"              52\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT190380GNMJ\\BNK\"      \"pkdailai\"      \"Internet Bank Payment\" \"0.00\"  \"20,000.00\"     \"520,351.02\"    \"\",\"              53\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038TTGSS\\BNK\"      \"transfer 2\"    \"Internet Bank Payment\" \"0.00\"  \"20,000.00\"     \"540,351.02\"    \"\",\"              54\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038WBYP2\\BNK\"      \"TRANSFER3\"     \"Internet Bank Payment\" \"0.00\"  \"11,000.00\"     \"551,351.02\"    \"\",\"              55\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038MGQ61\"  \"Clearing\"      \"Outward Cheque - Dr\"   \"-500,000.00\"   \"0.00\"  \"51,351.02\"     \"\",\"              56\"   \"20751025\"      \"20190208\"      \"20751025\"      \"20190208\"      \"FT190392X99W\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-351.00\"       \"0.00\"  \"51,000.02\"     \"\",\"              57\"   \"20751027\"      \"20190210\"      \"20751027\"      \"20190210\"      \"FT190416Y920\\BNK\"      \"9851121351\"    \"Mobile payment\"        \"0.00\"  \"1,500.00\"      \"52,500.02\"     \"\",\"              58\"   \"20751027\"      \"20190210\"      \"20751027\"      \"20190210\"      \"FT19041HJSPZ\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-1,500.00\"     \"0.00\"  \"51,000.02\"     \"\",\"              59\"   \"20751101\"      \"20190213\"      \"20751101\"      \"20190213\"      \"FT19044RF4X4\"  \"959\"   \"Transfer IPS Credit\"   \"0.00\"  \"1,596.00\"      \"52,596.02\"     \"\",\"              60\"   \"20751101\"      \"20190213\"      \"20751101\"      \"20190213\"      \"FT19044RF4X4\"  \"AC-0100200000009012\"   \"IPS Debit Charge\"      \"-10.00\"        \"0.00\"  \"52,586.02\"     \"\",\"              61\"   \"20751103\"      \"20190215\"      \"20751103\"      \"20190215\"      \"FT19046B2GJX\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-500.00\"       \"0.00\"  \"52,086.02\"     \"\",\"              62\"   \"20751105\"      \"20190217\"      \"20751105\"      \"20190217\"      \"FT1904808LMP\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-2,000.00\"     \"0.00\"  \"50,086.02\"     \"\",\"              63\"   \"20751106\"      \"20190218\"      \"20751106\"      \"20190218\"      \"FT19049X6NPQ\\BNK\"      \"9851121351\"    \"Mobile payment\"        \"0.00\"  \"13.98\" \"50,100.00\"     \"\",\"              64\"   \"20751110\"      \"20190222\"      \"20751110\"      \"20190222\"      \"FT19053P016C\\BNK\"      \"57500\" \"Internet Bank Payment\" \"0.00\"  \"7,400.00\"      \"57,500.00\"     \"\",\"              65\"   \"20751110\"      \"20190222\"      \"20751110\"      \"20190222\"      \"LD1110388842\"  \"\"      \"Principal Increase\"    \"0.00\"  \"69,763.40\"     \"127,263.40\"    \"\",\"              66\"   \"20751112\"      \"20190224\"      \"20751112\"      \"20190224\"      \"FT19055PB2MQ\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-7,500.00\"     \"0.00\"  \"119,763.40\"    \"\",\"              67\"   \"20751113\"      \"20190225\"      \"20751113\"      \"20190225\"      \"FT190568NDC8\\G10\"      \"BINDU L S\"     \"Transfer In\"   \"0.00\"  \"35,000.00\"     \"154,763.40\"    \"\",\"              68\"   \"20751115\"      \"20190227\"      \"20751115\"      \"20190227\"      \"FT190589LYCH\\BNK\"      \"158000\"        \"Internet Bank Payment\" \"0.00\"  \"3,236.60\"      \"158,000.00\"    \"\",\"              69\"   \"20751117\"      \"20190301\"      \"20751117\"      \"20190301\"      \"FT19060RQQQC\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-20,000.00\"    \"0.00\"  \"138,000.00\"    \"\",\"              70\"   \"20751117\"      \"20190301\"      \"20751117\"      \"20190301\"      \"FT19060197K1\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-20,000.00\"    \"0.00\"  \"118,000.00\"    \"\",\"              71\"   \"20751117\"      \"20190301\"      \"20751117\"      \"20190301\"      \"FT19060MFZ12\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-18,000.00\"    \"0.00\"  \"100,000.00\"    \"\",\"                \"   \"\"      \"\"      \"\"      \"\"      \"\"      \"\"      \"\"      \"-706,261.20\"   \"646,261.20\"    \"\"      \"\"";
//                    response = ",S.NO::S.NO/NEP.VDT::Nepali Value Date/VAL.DT::Value Date/NP.BDT::Nepali Booking Date/B.DT::Booking Date/T.REF::Trans Ref/NARR::Narrative/TXN.TYPE::Txn Type/CR.::CREDIT/OP.BAL::Balance/TOT.TOVR::Total Turnover,\"No records were found that matched the selection criteria\",\"NO RECORDS RETURNED BY ROUTINE BASED SELECTION\",\"                                                  \"\n";
                    statementDtoList = parseStatementResponse(response);
                    break;
                default:
                    log.warn("Not a Statement Type");
            }
        } else {
            logService.saveUserActionLog(username, command, null, String.valueOf(UserActionTypeEnum.STATEMENT_SEARCH));
        }
        stopConnection();
        if (statementType.equals(StatementTypeEnum.STATEMENT)) {
            return statementDtoList;
        } else {
            return miniStatementDtoList;
        }
    }

    public boolean telnetConnectionAndResponseForFundTransfer(String username, FundTransferTransaction fundTransferTransaction, String command) {
        setTelnetConfig();
        boolean responseStatus = false;
        boolean connectionStatus = startConnection(this.ip, this.port);
        if (connectionStatus) {
            String response = sendMessage(command);
            logService.saveUserActionLog(username, command, response, String.valueOf(UserActionTypeEnum.FUND_TRANSFER));//set log
            fundTransferTransaction.setTransactionId(getTransactionIdFromResponse(response));
            responseStatus = parseFundTransferResponse(response);
            stopConnection();
        } else {
            logService.saveUserActionLog(username, command, null, String.valueOf(UserActionTypeEnum.FUND_TRANSFER));//set log
        }
        return responseStatus;
    }

    private boolean parseFundTransferResponse(String response) {
        int indexOfSlash = StringUtils.ordinalIndexOf(response, "/", 2);
        String checkMe = String.valueOf(response.charAt(indexOfSlash + 1));
        return checkMe.equalsIgnoreCase("1");
    }

    private String getTransactionIdFromResponse(String response) {
        int indexOfSlash = StringUtils.ordinalIndexOf(response, "/", 1);
        String transactionId = response.substring(0, indexOfSlash);
        if (StringUtils.isNotBlank(transactionId)) {
            return transactionId;
        }
        return StringUtils.EMPTY;
    }

    /**
     * Successful response parsing
     * ,S.NO::S.NO/NEP.VDT::Nepali Value Date/VAL.DT::Value Date/NP.BDT::Nepali Booking Date/B.DT::Booking Date/T.REF::Trans Ref/NARR::Narrative/TXN.TYPE::Txn Type/CR.::CREDIT/OP.BAL::Balance/TOT.TOVR::Total Turnover,\"                \"    \"\"      \"\"      \"\"      \"\"      \"\"      \"Balance at Period Start\"       \"\"      \"\"      \"\"      \"160,000.00\"    \"\",\"               1\"   \"20750919\"      \"20190103\"      \"20750919\"      \"20190103\"      \"FT19003HF1D1\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"155,000.00\"    \"\",\"               2\"   \"20750923\"      \"20190107\"      \"20750923\"      \"20190107\"      \"FT190079LSRH\"  \"UMHL 5- CASH DI\"       \"Transfer IPS Credit\"   \"0.00\"  \"536.75\"        \"155,536.75\"    \"\",\"               3\"   \"20750923\"      \"20190107\"      \"20750923\"      \"20190107\"      \"FT190079LSRH\"  \"AC-0100200000009012\"   \"IPS Debit Charge\"      \"-10.00\"        \"0.00\"  \"155,526.75\"    \"\",\"               4\"   \"20750924\"      \"20190108\"      \"20750924\"      \"20190108\"      \"FT19008WY88D\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"150,526.75\"    \"\",\"               5\"   \"20750924\"      \"20190108\"      \"20750924\"      \"20190108\"      \"TT19008JF0C9\\F07\"      \"SHIVA AMATYA\"  \"Cash Withdrawal(Local Ccy)\"    \"-20,000.00\"    \"0.00\"  \"130,526.75\"    \"\",\"               6\"   \"20750927\"      \"20190111\"      \"20750927\"      \"20190111\"      \"FT190114GWDQ\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-5,000.00\"     \"0.00\"  \"125,526.75\"    \"\",\"               7\"   \"20750927\"      \"20190111\"      \"20750927\"      \"20190111\"      \"FT19011DC1K0\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-500.00\"       \"0.00\"  \"125,026.75\"    \"\",\"               8\"   \"20751001\"      \"20190115\"      \"20750930\"      \"20190114\"      \"0100200000009012-20190114\"     \"\"      \"Credit Interest\"       \"0.00\"  \"2,304.40\"      \"127,331.15\"    \"\",\"               9\"   \"20751001\"      \"20190115\"      \"20750930\"      \"20190114\"      \"0100200000009012-20190114\"     \"\"      \"Tax Amount Due\"        \"-115.22\"       \"0.00\"  \"127,215.93\"    \"\",\"              10\"   \"20751003\"      \"20190117\"      \"20751003\"      \"20190117\"      \"FT19017JH8W2\\BNK\"      \"145000\"        \"Internet Bank Payment\" \"0.00\"  \"17,784.07\"     \"145,000.00\"    \"\",\"              11\"   \"20751003\"      \"20190117\"      \"20751003\"      \"20190117\"      \"FT19017GVRKH\\BNK\"      \"164000\"        \"Internet Bank Payment\" \"0.00\"  \"19,000.00\"     \"164,000.00\"    \"\",\"              12\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018TM9HB\\BNK\"      \"NT Prepaid Topup\"      \"Mobile payment\"        \"-10.00\"        \"0.00\"  \"163,990.00\"    \"\",\"              13\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190186LXPX\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-5.00\" \"0.00\"  \"163,985.00\"    \"\",\"              14\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190189C3JY\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-5.00\" \"0.00\"  \"163,980.00\"    \"\",\"              15\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018DYQ8C\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,979.00\"    \"\",\"              16\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018WYJ9C\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,978.00\"    \"\",\"              17\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018L1PSQ\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,977.00\"    \"\",\"              18\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018F1DVK\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,976.00\"    \"\",\"              19\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018T6V92\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,975.00\"    \"\",\"              20\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT19018TC647\\BNK\"      \"9840273283\"    \"eSewa Payment\" \"-1.00\" \"0.00\"  \"163,974.00\"    \"\",\"              21\"   \"20751004\"      \"20190118\"      \"20751004\"      \"20190118\"      \"FT190188V6CT\\BNK\"      \"NT Prepaid Topup\"      \"Mobile payment\"        \"-20.00\"        \"0.00\"  \"163,954.00\"    \"\",\"              22\"   \"20751005\"      \"20190119\"      \"20751005\"      \"20190119\"      \"FT19019HB68B\\BNK\"      \"164\"   \"Mobile payment\"        \"0.00\"  \"46.00\" \"164,000.00\"    \"\",\"              23\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT190207NV81\\BNK\"      \"test v5\"       \"Mobile payment\"        \"0.00\"  \"10.00\" \"164,010.00\"    \"\",\"              24\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020V1Z34\\BNK\"      \"Ncell Topup\"   \"Mobile payment\"        \"-10.00\"        \"0.00\"  \"164,000.00\"    \"\",\"              25\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020R98V3\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-15,000.00\"    \"0.00\"  \"149,000.00\"    \"\",\"              26\"   \"20751006\"      \"20190120\"      \"20751006\"      \"20190120\"      \"FT19020NMCJ6\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"10.00\" \"149,010.00\"    \"\",\"              27\"   \"20751007\"      \"20190121\"      \"20751007\"      \"20190121\"      \"FT1902152G20\"  \"NMB 7475-011282\"       \"Transfer IPS Credit\"   \"0.00\"  \"8,325.00\"      \"157,335.00\"    \"\",\"              28\"   \"20751007\"      \"20190121\"      \"20751007\"      \"20190121\"      \"FT1902152G20\"  \"AC-0100200000009012\"   \"IPS Debit Charge\"      \"-20.00\"        \"0.00\"  \"157,315.00\"    \"\",\"              29\"   \"20751008\"      \"20190122\"      \"20751008\"      \"20190122\"      \"TT190220RFNF\\F17\"      \"SANTOSH DEO\"   \"Cash Withdrawal(Local Ccy)\"    \"-40,000.00\"    \"0.00\"  \"117,315.00\"    \"\",\"              30\"   \"20751010\"      \"20190124\"      \"20751010\"      \"20190124\"      \"FT19024YWZSW\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"112,315.00\"    \"\",\"              31\"   \"20751010\"      \"20190124\"      \"20751010\"      \"20190124\"      \"TT190243YDLP\\G18\"      \"KRISHNA PRASAD PAUDEL\" \"Deposit Cash(Local Currency)\"  \"0.00\"  \"50,000.00\"     \"162,315.00\"    \"\",\"              32\"   \"20751013\"      \"20190127\"      \"20751013\"      \"20190127\"      \"FT19027BR3CS\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"10.00\" \"162,325.00\"    \"\",\"              33\"   \"20751013\"      \"20190127\"      \"20751013\"      \"20190127\"      \"FT19027L3XYC\\BNK\"      \"nita\"  \"Mobile payment\"        \"0.00\"  \"100.00\"        \"162,425.00\"    \"\",\"              34\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028ZFXLV\\BNK\"      \"166000\"        \"Internet Bank Payment\" \"0.00\"  \"3,575.00\"      \"166,000.00\"    \"\",\"              35\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028WCXVW\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-10,000.00\"    \"0.00\"  \"156,000.00\"    \"\",\"              36\"   \"20751014\"      \"20190128\"      \"20751014\"      \"20190128\"      \"FT19028X61MD\\BNK\"      \"Test for 159\"  \"Mobile payment\"        \"0.00\"  \"3,000.00\"      \"159,000.00\"    \"\",\"              37\"   \"20751015\"      \"20190129\"      \"20751015\"      \"20190129\"      \"FT190292R2L2\\BNK\"      \"TSTTRF\"        \"Mobile payment\"        \"0.00\"  \"100.00\"        \"159,100.00\"    \"\",\"              38\"   \"20751015\"      \"20190129\"      \"20751015\"      \"20190129\"      \"FT190292RQ9J\\BNK\"      \"TestQR\"        \"Mobile payment\"        \"-100.00\"       \"0.00\"  \"159,000.00\"    \"\",\"              39\"   \"20751016\"      \"20190130\"      \"20751016\"      \"20190130\"      \"FT190301YCMP\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-5,000.00\"     \"0.00\"  \"154,000.00\"    \"\",\"              40\"   \"20751018\"      \"20190201\"      \"20751018\"      \"20190201\"      \"FT19032VDP73\\BNK\"      \"9847020585\"    \"eSewa Payment\" \"-5,000.00\"     \"0.00\"  \"149,000.00\"    \"\",\"              41\"   \"20751018\"      \"20190201\"      \"20751018\"      \"20190201\"      \"FT19032FGF3L\\BNK\"      \"9847020585\"    \"eSewa Payment\" \"-2,000.00\"     \"0.00\"  \"147,000.00\"    \"\",\"              42\"   \"20751020\"      \"20190203\"      \"20751020\"      \"20190203\"      \"FT19034M43WQ\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-4,000.00\"     \"0.00\"  \"143,000.00\"    \"\",\"              43\"   \"20751020\"      \"20190203\"      \"20751020\"      \"20190203\"      \"FT19034X19BW\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-2,000.00\"     \"0.00\"  \"141,000.00\"    \"\",\"              44\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT19035GDH9R\\BNK\"      \"41745\" \"Fonepay Payment\"       \"-829.00\"       \"0.00\"  \"140,171.00\"    \"\",\"              45\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT190354RWSG\\BNK\"      \"Pankaj 2 Santosh\"      \"Internet Bank Payment\" \"-5,000.00\"     \"0.00\"  \"135,171.00\"    \"\",\"              46\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"FT19035KBLN0\\BNK\"      \"ASBAALBSL1301310\"      \"Transfer Out\"  \"-10.00\"        \"0.00\"  \"135,161.00\"    \"\",\"              47\"   \"20751021\"      \"20190204\"      \"20751021\"      \"20190204\"      \"SB.0100200000009012\\BNK\"       \"\"      \"Cheque Issued Commission Dr\"   \"-950.00\"       \"0.00\"  \"134,211.00\"    \"\",\"              48\"   \"20751022\"      \"20190205\"      \"20751022\"      \"20190205\"      \"FT19036GLZLN\\BNK\"      \"Rev 00000009012\"       \"Transfer In\"   \"0.00\"  \"950.00\"        \"135,161.00\"    \"\",\"              49\"   \"20751022\"      \"20190205\"      \"20751022\"      \"20190205\"      \"FT19036C57LV\\BNK\"      \"\"      \"POS TRANSACTION\"       \"-3,809.98\"     \"0.00\"  \"131,351.02\"    \"\",\"              50\"   \"20751023\"      \"20190206\"      \"20751023\"      \"20190206\"      \"FT1903792XHH\"  \"PANKAJ\"        \"Transfer In\"   \"0.00\"  \"371,000.00\"    \"502,351.02\"    \"\",\"              51\"   \"20751023\"      \"20190206\"      \"20751023\"      \"20190206\"      \"FT1903784CG8\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-2,000.00\"     \"0.00\"  \"500,351.02\"    \"\",\"              52\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT190380GNMJ\\BNK\"      \"pkdailai\"      \"Internet Bank Payment\" \"0.00\"  \"20,000.00\"     \"520,351.02\"    \"\",\"              53\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038TTGSS\\BNK\"      \"transfer 2\"    \"Internet Bank Payment\" \"0.00\"  \"20,000.00\"     \"540,351.02\"    \"\",\"              54\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038WBYP2\\BNK\"      \"TRANSFER3\"     \"Internet Bank Payment\" \"0.00\"  \"11,000.00\"     \"551,351.02\"    \"\",\"              55\"   \"20751024\"      \"20190207\"      \"20751024\"      \"20190207\"      \"FT19038MGQ61\"  \"Clearing\"      \"Outward Cheque - Dr\"   \"-500,000.00\"   \"0.00\"  \"51,351.02\"     \"\",\"              56\"   \"20751025\"      \"20190208\"      \"20751025\"      \"20190208\"      \"FT190392X99W\\BNK\"      \"9851121351\"    \"eSewa Payment\" \"-351.00\"       \"0.00\"  \"51,000.02\"     \"\",\"              57\"   \"20751027\"      \"20190210\"      \"20751027\"      \"20190210\"      \"FT190416Y920\\BNK\"      \"9851121351\"    \"Mobile payment\"        \"0.00\"  \"1,500.00\"      \"52,500.02\"     \"\",\"              58\"   \"20751027\"      \"20190210\"      \"20751027\"      \"20190210\"      \"FT19041HJSPZ\\BNK\"      \"\"      \"ATM WITHDRAWAL\"        \"-1,500.00\"     \"0.00\"  \"51,000.02\"     \"\",\"              59\"   \"20751101\"      \"20190213\"      \"20751101\"      \"20190213\"      \"FT19044RF4X4\"  \"959\"   \"Transfer IPS Credit\"   \"0.00\"  \"1,596.00\"      \"52,596.02\"     \"\",\
     *
     * @param response
     * @return
     */
    private List<StatementDto> parseStatementResponse(String response) {
        List<StatementDto> statementDtoList = new ArrayList<>();
        try {
            String firstReplacedString = response.replaceFirst(",", "!~").replaceFirst(",", "!~").replaceAll(",\"", "!~\"").replaceAll("::", "\"\"");
            String secondReplacedString = firstReplacedString.replaceFirst("S.NO", "\"S.NO");
            String thirdReplaceString = secondReplacedString.replaceFirst("Total Turnover", "Total Turnover\"");
            StringTokenizer stringTokenizer = new StringTokenizer(thirdReplaceString, "!~");
            while (stringTokenizer.hasMoreElements()) {
                String[] strings = StringUtils.substringsBetween((String) stringTokenizer.nextToken(), "\"", "\"");
                StatementDto statementDto = new StatementDto();
                statementDto.setVariableOne(strings[0]);
                statementDto.setVariableTwo(strings[1]);
                statementDto.setVariableThree(strings[2]);
                statementDto.setVariableFour(strings[3]);
                statementDto.setVariableFive(strings[4]);
                statementDto.setVariableSix(strings[5]);
                statementDto.setVariableSeven(strings[6]);
                statementDto.setVariableEight(strings[7]);
                statementDto.setVariableNine(strings[8]);
                statementDto.setVariableTen(strings[9]);
                statementDto.setVariableEleven(strings[10]);
                statementDto.setVariableTwelve(strings[11]);
                statementDtoList.add(statementDto);
            }
        } catch (Exception e) {
            log.error("Statement Parse Exception\n" + e);
        }
        return statementDtoList;
    }

    /**
     * Response param must be in below format
     * ,BOOKING.DATE::Booking Date/Trans.ID::Trans.ID/Trans.Type::Trans Type/Cheque.No::Cheque No/Amount::Amount/Narrative::Narrative,"27 JAN 2019 " "FT190278ZK9R\BNK" "Outward Cheque -" "4 " "-124.00 " "CHECK 4 ","27 JAN 2019 " "TT19027ZLKGN\BNK" "Transfer Debit " "3 " "-122.00 " "CHEQUE CHECK 3 ","27 JAN 2019 " "TT190270FD94\BNK" "Deposit Cash(Loc" "5 " "125.00 " "O/W Clearing ECC","27 JAN 2019 " "TT19027DMNMR\BNK" "Cash Withdrawal(" "2 " "-121.00 " "CHEQUE CHECK 2 ","27 JAN 2019 " "TT190270HK18\BNK" "Cash Withdrawal(" "1 " "-120.00 " "CHEQUE CHECK ","27 JAN 2019 " "FT19027D1KFK " "Transfer IPS Cre" " " "1,250.00 " "TESTCR ","27 JAN 2019 " "FT19027109DN " "Transfer IPS Cre" " " "1,251.00 " "TESTCR ","CURRENT BALANCE " " " " " " " "156,303.81 " " "
     *
     * @param response
     * @return
     */
    private List<MiniStatementDto> parseMiniStatementResponse(String response) {
        List<MiniStatementDto> miniStatementDtos = new ArrayList<>();
        try {
            // deleting unused part
            StringBuilder stringBuilder = new StringBuilder(response);
            int firstQuotationIndex = stringBuilder.indexOf("\"", 1);
            stringBuilder.delete(0, firstQuotationIndex);

            // splitting main data part into list
            String dataAfterRemovingFirstPart = stringBuilder.toString(); // main data working fine
            String replacingCommaaByColons = dataAfterRemovingFirstPart.replace("\",\"", "\"::\"");
            String[] splittedmaindatas = replacingCommaaByColons.split("::");

            List<List<String>> responseList = new ArrayList<>();
            // for first list value of splittedmaindatas
            for (String data : splittedmaindatas) {
                String pre = "\"";
                String post = "\"";
                List<String> strings = Arrays.asList(data.replaceAll("^.*?" + pre, "").split(post + ".*?(\"|$)"));
                responseList.add(strings);
            }
            for (List<String> oneList : responseList) {
                MiniStatementDto miniStatementDto = new MiniStatementDto();
                miniStatementDto.setBookingDateEn(oneList.get(0));
                miniStatementDto.setTransactionId(oneList.get(1));
                miniStatementDto.setTransactionType(oneList.get(2));
                miniStatementDto.setChequeNo(oneList.get(3));
                miniStatementDto.setAmount(oneList.get(4));
                miniStatementDto.setNarrative(oneList.get(5));
                miniStatementDtos.add(miniStatementDto);
            }
        } catch (Exception e) {
            log.error("Mini Statement Parse Exception\n" + e);
        }
        return miniStatementDtos;
    }


    private static boolean startConnection(String ip, int port) {
        if (StringUtils.isNotBlank(ip) && port > 0) {
            log.info(ip + " " + port);
            try {
                clientSocket = new Socket(ip, port);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                return true;
            } catch (Exception e) {
                log.error("Exception\n" + e);
            }
        }
        return false;
    }

    private String sendMessage(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            String respLine1 = "";
            String respLine2 = "";
            String respLine3 = "";
            String respLine4 = "";
            try {
                Thread.sleep(1000);
                out.println(msg);
                respLine1 = in.readLine();
                System.out.println(respLine1);
                respLine2 = in.readLine();
                System.out.println(respLine2);
                respLine3 = in.readLine();
                System.out.println(respLine3);
                respLine4 = in.readLine();
                System.out.println(respLine4);
            } catch (Exception e) {
                log.error("Exception\n" + e);
            }
            return respLine4;
        }
        log.error("Command not found.");
        return StringUtils.EMPTY;
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            log.error("Exception\n" + e);
        }
    }
}
