package com.digi.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.digi.app.user.Office;
import com.digi.app.user.Users;
import com.digi.app.user.repository.UsersRepo;

import java.util.List;

@Component
public class UsersComponent {
    private UsersRepo usersRepo;
    private CrudReturnService<Users> crudReturnService;

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Autowired
    public void setCrudReturnService(CrudReturnService<Users> crudReturnService) {
        this.crudReturnService = crudReturnService;
    }

    public List<Users> findUsersByCurrentUsersStaffsOffice(String currentUsername) {
        Office currentUserOffice = findOfficeByCurrentUsers(currentUsername);
        return usersRepo.findByStaffsOffice(currentUserOffice);
    }

    public ResponseEntity<?> getReturn(List<Users> list) {
        return crudReturnService.controllerReturnForList(list);
    }

    public Office findOfficeByCurrentUsers(String currentUsername) {
        Users currentUser = usersRepo.findByUsername(currentUsername);
        return currentUser.getStaffs().getOffice();
    }
}
