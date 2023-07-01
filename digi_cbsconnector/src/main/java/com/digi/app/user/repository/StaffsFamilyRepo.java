package com.digi.app.user.repository;

import com.digi.app.user.StaffsFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


import org.javers.spring.annotation.JaversSpringDataAuditable;

@Repository
@JaversSpringDataAuditable
public interface StaffsFamilyRepo extends JpaRepository<StaffsFamily, Integer> {
    List<StaffsFamily> findByStaffsCode(String code);
}
