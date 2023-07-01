package com.digi.app.user.repository;

import com.digi.app.user.Office;
import com.digi.app.user.Staffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface StaffsRepo extends JpaRepository<Staffs, String> {
    List<Staffs> findByOffice(Office office);
}
