package com.digi.app.adminconfig.service;

import com.digi.app.adminconfig.entity.ConfigEntity;
import com.digi.app.adminconfig.repository.ConfigEntityRepository;
import com.digi.app.util.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AdminConfigServiceImpl implements AdminConfigService {
    private ConfigEntityRepository configEntityRepository;
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setConfigEntityRepository(ConfigEntityRepository configEntityRepository) {
        this.configEntityRepository = configEntityRepository;
    }

    @Override
    public Optional<ConfigEntity> updateConfigEntity(ConfigEntity configEntity) {
        if (!StringUtils.isEmpty(configEntity.getTelnetPassword()) && configEntity.getTelnetPassword() != null) {
            String encryptedPwd = tokenService.encrypt(configEntity.getTelnetPassword());
            configEntity.setTelnetPassword(encryptedPwd);
        }
        return Optional.of(configEntityRepository.save(configEntity));
    }

    @Override
    public Optional<ConfigEntity> getConfigEntity() {
        Optional<ConfigEntity> configEntity = configEntityRepository.findById(1);
        return configEntity;
    }
}
