package com.digi.app.adminconfig.controller;

import com.digi.app.adminconfig.entity.ConfigEntity;
import com.digi.app.adminconfig.service.AdminConfigService;
import com.digi.app.masteraccounts.MasterAccounts;
import com.digi.app.masteraccounts.MasterAccountsDto;
import com.digi.app.masteraccounts.MasterAccountsService;
import com.digi.app.message.HttpResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admins")
public class AdminConfigController {
    private AdminConfigService adminConfigService;
    private MasterAccountsService masterAccountsService;

    @Autowired
    public void setMasterAccountsService(MasterAccountsService masterAccountsService) {
        this.masterAccountsService = masterAccountsService;
    }

    @Autowired
    public void setAdminConfigService(AdminConfigService adminConfigService) {
        this.adminConfigService = adminConfigService;
    }

    @GetMapping("/forms")
    public ModelAndView getForm() {
        return new ModelAndView("configs/create");
    }

    @PostMapping("/configs")
    public ResponseEntity<?> updateConfigEntity(@RequestBody ConfigEntity configEntity) {
        Optional<ConfigEntity> configEntityOptional = adminConfigService.updateConfigEntity(configEntity);
        return configEntityOptional.map(entity -> new ResponseEntity<>(HttpResponses.fetched(entity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/configs")
    public ResponseEntity<?> getConfigEntity() {
        Optional<ConfigEntity> configEntityOptional = adminConfigService.getConfigEntity();
        configEntityOptional.ifPresent(configEntity -> configEntity.setTelnetPassword(null));
        return configEntityOptional.map(configEntity -> new ResponseEntity<>(HttpResponses.fetched(configEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/master/accounts")
    public ResponseEntity<?> createMasterAccounts(@RequestBody MasterAccountsDto masterAccountsDto) {
        Optional<MasterAccounts> masterAccounts = masterAccountsService.assembleToDomain(0, masterAccountsDto);
        if (masterAccounts.isPresent()) {
            Optional<MasterAccounts> savedMasterAccounts = masterAccountsService.save(masterAccounts.get());
            if (savedMasterAccounts.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedMasterAccounts.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/master/accounts/{id}")
    public ResponseEntity<?> updateMasterAccounts(@PathVariable int id, @RequestBody MasterAccountsDto masterAccountsDto) {
        Optional<MasterAccounts> masterAccounts = masterAccountsService.assembleToDomain(id, masterAccountsDto);
        if (masterAccounts.isPresent()) {
            Optional<MasterAccounts> savedMasterAccounts = masterAccountsService.save(masterAccounts.get());
            if (savedMasterAccounts.isPresent()) {
                return new ResponseEntity<>(HttpResponses.created(savedMasterAccounts.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/master/accounts")
    public ResponseEntity<?> findAllMasterAccounts() {
        List<MasterAccounts> masterAccounts = masterAccountsService.findAll();
        if (!masterAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(masterAccounts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }
}
