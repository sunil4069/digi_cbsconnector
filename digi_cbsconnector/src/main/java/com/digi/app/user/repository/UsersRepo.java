package com.digi.app.user.repository;

import com.digi.app.user.Office;
import com.digi.app.user.Users;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface UsersRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

    Optional<Users> findByStaffsCode(String staffCode);

    List<Users> findByStaffsOffice(Office office);

    Users findByUsernameAndAuthorized(String username, String authorizedYesNo);

    Users findByUsernameAndAuthorizedAndStatus(String username, String authorizedYesNo, boolean status);

    List<Users> findByAuthorized(String authorizedYesNo);

}
