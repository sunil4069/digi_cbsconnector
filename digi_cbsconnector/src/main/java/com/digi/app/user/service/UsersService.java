package com.digi.app.user.service;

import com.digi.app.component.UsersComponent;
import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.message.HttpResponses;
import com.digi.app.user.Users;
import com.digi.app.user.enums.YesNoEnum;
import com.digi.app.user.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private UsersComponent usersComponent;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsersRepo usersRepo;

    @Autowired
    public void setUsersComponent(UsersComponent usersComponent) {
        this.usersComponent = usersComponent;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public ResponseEntity<?> findByCurrentUsersStaffsOffice(String currentUsername) {
        List<Users> list = usersComponent.findUsersByCurrentUsersStaffsOffice(currentUsername);
        return new ResponseEntity<>(HttpResponses.fetched(list), HttpStatus.OK);
    }

    public Users createUser(Users users) {
        Users existingUserByUsername = usersRepo.findByUsername(users.getUsername());
        if (existingUserByUsername != null) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_ALREADY_EXISTS));
        }
        Optional<Users> existingUserByStaffCodeOptional = usersRepo.findByStaffsCode(users.getStaffs().getCode());
        if (existingUserByStaffCodeOptional.isPresent()) {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.STAFF_IS_ALREADY_USER));
        }
        String password = users.getPassword();
        String confirmPassword = users.getConfirmpassword();

        String encpassword = checkPasswordConfirmPassword(password, confirmPassword);
        if (encpassword.length() > 0) {
            users.setPassword(encpassword);
            users = usersRepo.save(users);
        }
        return users;
    }

    public String checkPasswordConfirmPassword(String password, String confirmPassword) {
        String encpassword = "";
        if (password.equals(confirmPassword)) {
            encpassword = bCryptPasswordEncoder.encode(password);
        }
        return encpassword;
    }

    public Optional<Users> authorizeUser(String username, String principalUser) {
        Users users = usersRepo.findByUsername(username);
        if (users != null) {
            if (users.getAuthorized().equals(YesNoEnum.YES.getValue())) {
                throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_ALREADY_AUTHORIZED));
            } else {
                users.setAuthorizedBy(principalUser);
                users.setLastUpdatedBy(principalUser);
                users.setAuthorized(YesNoEnum.YES.getValue());
                users.setStatus(true);
                return Optional.of(usersRepo.save(users));
            }
        } else {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_NOT_FOUND));
        }
    }

    public Optional<Users> activateUser(String username, String principalUser) {
        Users users = usersRepo.findByUsername(username);
        if (users != null) {
            if (users.isStatus()) {
                throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_ALREADY_ACTIVATED));
            } else {
                users.setStatus(true);
                users.setLastUpdatedBy(principalUser);
                return Optional.of(usersRepo.save(users));
            }
        } else {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_NOT_FOUND));
        }
    }

    public Optional<Users> deactivateUser(String username, String principalUser) {
        Users users = usersRepo.findByUsername(username);
        if (users != null) {
            if (!users.isStatus()) {
                throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_ALREADY_DEACTIVATED));
            } else {
                users.setStatus(false);
                users.setLastUpdatedBy(principalUser);
                return Optional.of(usersRepo.save(users));
            }
        } else {
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_NOT_FOUND));
        }
    }

    public Optional<Users> getUser(String username) {
        return Optional.of(usersRepo.findByUsername(username));
    }
}
