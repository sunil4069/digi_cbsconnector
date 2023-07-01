package com.digi.app.config;

public class JspPathConfig {
    /**
     * Dashboard jsp path
     */
    public static final String DASHBOARD = "dashboard";
    /**
     * Access denied jsp path
     */
    public static final String FORBIDDEN = "page-403";
    /**
     * Index jsp path
     */
    public static final String INDEX = "index";
    /**
     * Fund transfer jsp paths
     */
    public static final String FUND_TRANSFER_CREATE_FORM = "transaction/fund-transfer-form";
    public static final String ACCOUNT_TRANSFER_CREATE_FORM = "transaction/account-transfer-form";
    public static final String FUND_TRANSFER_UNAUTHORIZED_TABLE = "transaction/unauthorized-data";
    public static final String FUND_TRANSFER_AUTHORIZED_TABLE = "transaction/authorized-data";
    /**
     * Statement jsp paths
     */
    public static final String MINI_STATEMENT_SEARCH = "statement/mini-statement-search";
    public static final String STATEMENT_SEARCH = "statement/statement-search";


    public static final String MASTER_ACCOUNT_SAVINGS_CREATE_FORM = "masteraccounts/savings";
    public static final String MASTER_ACCOUNT_ERF_LOAN_CREATE_FORM = "masteraccounts/erfloan";
    public static final String MASTER_ACCOUNT_PF_LOAN_CREATE_FORM = "masteraccounts/pfloan";
}
