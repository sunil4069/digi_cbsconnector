package com.digi.app.adminconfig.service;

import com.digi.app.adminconfig.entity.ConfigEntity;

import java.util.Optional;

public interface AdminConfigService {
    Optional<ConfigEntity> updateConfigEntity(ConfigEntity configEntity);
    Optional<ConfigEntity> getConfigEntity();
}
