package com.digi.app.masteraccounts;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterAccountsServiceImpl implements MasterAccountsService {
    private MasterAccountsRepository masterAccountsRepository;

    @Autowired
    public void setMasterAccountsRepository(MasterAccountsRepository masterAccountsRepository) {
        this.masterAccountsRepository = masterAccountsRepository;
    }

    @Override
    public Optional<MasterAccounts> save(MasterAccounts masterAccounts) {
        return Optional.of(masterAccountsRepository.save(masterAccounts));
    }

    @Override
    public List<MasterAccounts> findAll() {
        return masterAccountsRepository.findAll();
    }

    @Override
    public Optional<MasterAccounts> assembleToDomain(int id, MasterAccountsDto masterAccountsDto) {
        MasterAccounts masterAccounts = new MasterAccounts();
        BeanUtils.copyProperties(masterAccountsDto, masterAccounts);
        masterAccounts.setAccountType(String.valueOf(masterAccountsDto.getAccountType()));
        if (id > 0) {
            masterAccounts.setId(id);
        }
        return Optional.of(masterAccounts);
    }

    @Override
    public Optional<MasterAccounts> assembleLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto) {
        MasterAccounts masterAccounts = new MasterAccounts();
        BeanUtils.copyProperties(masterAccountsDto, masterAccounts);
        masterAccounts.setAccountType(String.valueOf(AccountTypeEnum.SAVINGS));
        if (id > 0) {
            masterAccounts.setId(id);
        }
        return Optional.of(masterAccounts);
    }
    @Override
    public Optional<MasterAccounts> assemblePfLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto) {
        MasterAccounts masterAccounts = new MasterAccounts();
        BeanUtils.copyProperties(masterAccountsDto, masterAccounts);
        masterAccounts.setAccountType(String.valueOf(AccountTypeEnum.PFLOAN));
        if (id > 0) {
            masterAccounts.setId(id);
        }
        return Optional.of(masterAccounts);
    }

    @Override
    public Optional<MasterAccounts> assembleErfLoanAccountToDomain(int id, MasterAccountsDto masterAccountsDto) {
        MasterAccounts masterAccounts = new MasterAccounts();
        BeanUtils.copyProperties(masterAccountsDto, masterAccounts);
        masterAccounts.setAccountType(String.valueOf(AccountTypeEnum.ERFLOAN));
        if (id > 0) {
            masterAccounts.setId(id);
        }
        return Optional.of(masterAccounts);
    }

    @Override
    public List<MasterAccounts> findByAccountTypeIn(List<String> accountTypes) {
        return masterAccountsRepository.findByAccountTypeIn(accountTypes);
    }

    @Override
    public Optional<MasterAccounts> findByAccountNumber(String accountNumber) {
        return masterAccountsRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Optional<MasterAccounts> findById(int id) {
        return masterAccountsRepository.findById(id);
    }

    @Override
    public Optional<MasterAccounts> findSavingsById(int id) {
        return masterAccountsRepository.findByIdAndAccountType(id, String.valueOf(AccountTypeEnum.SAVINGS));
    }

    @Override
    public Optional<MasterAccounts> findErfLoanById(int id) {
        return masterAccountsRepository.findByIdAndAccountType(id, String.valueOf(AccountTypeEnum.ERFLOAN));
    }

    @Override
    public Optional<MasterAccounts> findPfLoanById(int id) {
        return masterAccountsRepository.findByIdAndAccountType(id, String.valueOf(AccountTypeEnum.PFLOAN));
    }
}
