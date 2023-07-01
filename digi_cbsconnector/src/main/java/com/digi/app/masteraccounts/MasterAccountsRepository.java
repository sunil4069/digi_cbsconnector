package com.digi.app.masteraccounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface MasterAccountsRepository extends JpaRepository<MasterAccounts, Integer> {
    List<MasterAccounts> findByAccountTypeIn(List<String> accountTypes);
    Optional<MasterAccounts> findByAccountNumberAndAccountType(String accountNumber, String accountType);
    Optional<MasterAccounts> findByAccountNumber(String accountNumber);
    Optional<MasterAccounts> findByIdAndAccountType(int id, String accountType);
}
