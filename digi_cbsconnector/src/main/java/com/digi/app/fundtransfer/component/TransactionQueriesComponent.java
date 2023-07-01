package com.digi.app.fundtransfer.component;

public class TransactionQueriesComponent {
    public static String fundTransferQuery(){
        String query="FUNDS.TRANSFER,IPS.EPAYMENT/I/,<username>/<password>,,DEBIT.ACCT.NO=<drAccountNumber>,DEBIT.CURRENCY=NPR,DEBIT.AMOUNT=<drAmount>,DEBIT.THEIR.REF=<drNarrative>,CREDIT.ACCT.NO=<crAccountNumber>,CREDIT.THEIR.REF=<crNarrative>,COMMISSION.CODE=D,COMMISSION.TYPE=IPSCOMMTRAC,COMMISSION.AMT=NPR101,IPS.TXN.ID=<systemTransactionId>";
        return query;
    }
}
