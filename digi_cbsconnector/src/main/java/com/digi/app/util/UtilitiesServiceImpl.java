package com.digi.app.util;

import com.digi.app.user.Roles;
import com.digi.app.user.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UtilitiesServiceImpl implements UtilitiesService {
    private UsersRepo usersRepo;

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<String> currentUserRoles(Principal principal) {
        Collection<Roles> roles = usersRepo.findByUsername(principal.getName()).getRoles();
        List<String> rolesList = new ArrayList<>();
        for (Roles role : roles) {
            rolesList.add(role.getName());
        }
        return rolesList;
    }

    public String currentUsername(Principal principal) {
        return principal.getName();
    }

    public String getDigiUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String getCurrentDateInDigiDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
}
