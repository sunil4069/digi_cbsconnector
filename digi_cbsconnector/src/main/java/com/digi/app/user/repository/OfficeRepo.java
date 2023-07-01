package com.digi.app.user.repository;

import com.digi.app.user.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface OfficeRepo extends JpaRepository<Office, Integer> {
}
