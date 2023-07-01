package com.digi.app.masteraccounts;

import java.util.List;
import java.util.Optional;

public interface MasterAccountsService {
    Optional<MasterAccounts> save(MasterAccounts masterAccounts);
    List<MasterAccounts> findAll();
    Optional<MasterAccounts> assembleToDomain(int id,MasterAccountsDto masterAccountsDto);
    Optional<MasterAccounts> assembleLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto);
    Optional<MasterAccounts> assembleErfLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto);
    Optional<MasterAccounts> assemblePfLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto);
    List<MasterAccounts> findByAccountTypeIn(List<String> accountTypes);
    Optional<MasterAccounts> findByAccountNumber(String accountNumber);
    Optional<MasterAccounts> findById(int id);
    Optional<MasterAccounts> findSavingsById(int id);
    Optional<MasterAccounts> findErfLoanById(int id);
    Optional<MasterAccounts> findPfLoanById(int id);
}
