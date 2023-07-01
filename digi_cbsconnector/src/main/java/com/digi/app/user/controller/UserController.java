package com.digi.app.user.controller;

import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.message.HttpResponses;
import com.digi.app.user.Roles;
import com.digi.app.user.Users;
import com.digi.app.user.dto.UpdateUserDto;
import com.digi.app.user.enums.RolesEnum;
import com.digi.app.user.enums.YesNoEnum;
import com.digi.app.user.repository.RolesRepo;
import com.digi.app.user.repository.StaffsRepo;
import com.digi.app.user.repository.UsersRepo;
import com.digi.app.user.service.UsersService;
import com.digi.app.util.UtilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    private UsersRepo usersRepo;
    private StaffsRepo staffsRepo;
    private UsersService usersService;
    private RolesRepo rolesRepo;
    private UtilitiesService utilitiesService;


    @Autowired
    public void setRolesRepo(RolesRepo rolesRepo) {
        this.rolesRepo = rolesRepo;
    }

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Autowired
    public void setStaffsRepo(StaffsRepo staffsRepo) {
        this.staffsRepo = staffsRepo;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setUtilitiesService(UtilitiesService utilitiesService) {
        this.utilitiesService = utilitiesService;
    }

    @GetMapping(path = "/create-page")
    public ModelAndView createpage() {
        ModelAndView model = new ModelAndView("users/create");
        model.addObject("pagetitle", "USERS");
        model.addObject("staffs", staffsRepo.findAll());
        return model;
    }

    @GetMapping(path = "/unauthorized-page")
    public ModelAndView unauthorizedUsersPage() {
        ModelAndView model = new ModelAndView("users/unauthorizedusers");
        model.addObject("pagetitle", "UNAUTHORIZED USERS");
        return model;
    }


    @GetMapping(path = "/specificUsers")
    public ResponseEntity<?> read(@ModelAttribute("username") String currentUsername) {
        ResponseEntity<?> returntype = usersService.findByCurrentUsersStaffsOffice(currentUsername);
        return returntype;

    }

    @GetMapping()
    public ResponseEntity<?> readAllUsers(@ModelAttribute("username") String currentUsername) {
        return new ResponseEntity<>(HttpResponses.fetched(usersRepo.findAll()), HttpStatus.OK);

    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<?> readOne(@PathVariable String username) {
        try {
            Users users = usersRepo.findByUsername(username);

            if (users != null) {
                return new ResponseEntity<Users>(users, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Users users, Principal principal) {
        List<String> roles = utilitiesService.currentUserRoles(principal);
        List<Integer> rolesIds = new ArrayList<>();
        users.getRoles().forEach(role -> rolesIds.add(role.getId()));
        if (rolesIds.contains(RolesEnum.SUPERADMIN.getId()) && roles.contains(RolesEnum.ADMIN.getName())) {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            users.setLastUpdatedBy(principal.getName());
            Users savedUsers = usersService.createUser(users);
            if (savedUsers != null) {
                return new ResponseEntity<>(HttpResponses.created(savedUsers), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("User create exception\n" + e);
            throw new CustomValidationException(Collections.singletonList(ErrorMessageConstants.UNABLE_TO_SAVE_USER + users.getUsername()));
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping("/roles/{username}")
    public ResponseEntity<?> updateUserRoleTxnLimit(@PathVariable String username, @RequestBody UpdateUserDto updateUserDto, Principal principal) {
        List<String> roles = updateUserDto.getRoles();
        List<String> currentUserRoles = utilitiesService.currentUserRoles(principal);
        if (roles.contains(String.valueOf(RolesEnum.SUPERADMIN.getId())) && currentUserRoles.contains(RolesEnum.ADMIN.getName())) {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            Users user = usersRepo.findByUsername(username);
            if (user != null) {
                List<Roles> rolesList = new ArrayList<>();
                roles.forEach(role -> {
                    Optional<Roles> roleOptional = rolesRepo.findById(Integer.parseInt(role));
                    roleOptional.ifPresent(rolesList::add);
                });
                user.setRoles(rolesList);
                user.setLastUpdatedBy(principal.getName());
                user.setTxnlimit(updateUserDto.getTxnlimit());
                Users savedUser = usersRepo.save(user);
                return new ResponseEntity<>(HttpResponses.created(savedUser), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("User role update error.\n" + e);
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping(path = "/unauthorized")
    public ResponseEntity<?> getUnauthorizedUsers() {
        List<Users> usersList = usersRepo.findByAuthorized(YesNoEnum.NO.getValue());
        if (!usersList.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(usersList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping(path = "/authorized")
    public ResponseEntity<?> getAuthorizedUsers(Principal principal) {
        List<Users> usersList = usersRepo.findByAuthorized(YesNoEnum.YES.getValue());
        Optional<Users> currentUserOptional = usersList.stream().filter(users -> users.getUsername().equals(principal.getName())).findFirst();
        currentUserOptional.ifPresent(usersList::remove);
        if (!usersList.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(usersList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping(path = "/{username}/authorize")
    public ResponseEntity<?> authorizeUser(@NotNull @PathVariable String username, Principal principal) {
        Optional<Users> usersOptional = usersService.authorizeUser(username, principal.getName());
        if (usersOptional.isPresent()) {
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping(path = "/{username}/activate")
    public ResponseEntity<?> activateUser(@NotNull @PathVariable String username, Principal principal) {
        Optional<Users> usersOptional = usersService.activateUser(username, principal.getName());
        if (usersOptional.isPresent()) {
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping(path = "/{username}/deactivate")
    public ResponseEntity<?> deactivateUser(@NotNull @PathVariable String username, Principal principal) {
        Optional<Users> usersOptional = usersService.deactivateUser(username, principal.getName());
        if (usersOptional.isPresent()) {
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
