package com.digi.app.user.controller;

import com.digi.app.component.CrudReturnService;
import com.digi.app.exception.ValidationErrorException;
import com.digi.app.message.HttpResponses;
import com.digi.app.user.StaffsFamily;
import com.digi.app.user.repository.StaffsFamilyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("staffsFamily")
public class StaffsFamilyController {
    private StaffsFamilyRepo staffsFamilyRepo;
    private CrudReturnService<StaffsFamily> crudReturnService;

    @Autowired
    public void setStaffsFamilyRepo(StaffsFamilyRepo staffsFamilyRepo) {
        this.staffsFamilyRepo = staffsFamilyRepo;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody StaffsFamily staffsFamily, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult);
        } else {
            StaffsFamily savedStaffFamily = staffsFamilyRepo.save(staffsFamily);
            return new ResponseEntity<>(HttpResponses.created(savedStaffFamily), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> read() {
        List<StaffsFamily> list = staffsFamilyRepo.findAll();
        ResponseEntity<?> returntype = crudReturnService.controllerReturnForList(list);
        return returntype;
    }

    @GetMapping(path = "/findByStaff/{code}")
    public ResponseEntity<?> findByStaff(@PathVariable String code) {
        List<StaffsFamily> list = staffsFamilyRepo.findByStaffsCode(code);

        if (list != null) {
            if (list.size() > 0) {
                return new ResponseEntity<>(HttpResponses.fetched(list), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        boolean stafffamily_present = staffsFamilyRepo.findById(id).isPresent();

        if (stafffamily_present) {
            staffsFamilyRepo.deleteById(id);
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

}
