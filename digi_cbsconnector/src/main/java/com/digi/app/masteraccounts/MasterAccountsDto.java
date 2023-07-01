package com.digi.app.masteraccounts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterAccountsDto {
    private int id;
    private String refStaffCode;
    private String accountName;
    private String accountNumber;
    private AccountTypeEnum accountType;
}
