package com.digi.app.config;

import com.digi.app.user.Roles;
import com.digi.app.user.Users;
import com.digi.app.user.enums.RolesEnum;
import com.digi.app.user.enums.YesNoEnum;
import com.digi.app.user.repository.RolesRepo;
import com.digi.app.user.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class StartupComponent {
    @Value("${initial.user.name}")
    private String username;
    @Value("${initial.user.password}")
    private String password;

    private UsersRepo usersRepo;
    private RolesRepo rolesRepo;

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }


    @Autowired
    public void setRolesRepo(RolesRepo rolesRepo) {
        this.rolesRepo = rolesRepo;
    }

    private void saveRoles() {
        if (rolesRepo.findAll().isEmpty()) {
            List<Roles> rolesList = new ArrayList<>();
            for (RolesEnum role : RolesEnum.values()) {
                Roles roles = new Roles();
                roles.setId(role.getId());
                roles.setName(role.getName());
                rolesList.add(roles);
            }
            rolesRepo.saveAll(rolesList);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        saveRoles();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Users user = usersRepo.findByUsername(username);
        if (user == null) {
            user = new Users();
            user.setUsername(username);
            user.setPassword(encoder.encode(password));
            user.setStatus(true);
            user.setAuthorized(YesNoEnum.YES.getValue());
            user.setAuthorizedBy(username);
            Roles roles = new Roles();
            roles.setId(RolesEnum.SUPERADMIN.getId());
            user.setRoles(Collections.singleton(roles));
            usersRepo.save(user);
        }
    }
}
