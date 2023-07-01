package com.digi.app.adminconfig.repository;

import com.digi.app.adminconfig.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface ConfigEntityRepository extends JpaRepository<ConfigEntity, Integer> {
}
