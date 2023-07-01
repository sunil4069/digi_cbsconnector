package com.digi.app.user.controller;

import com.digi.app.exception.ValidationErrorException;
import com.digi.app.message.HttpResponses;
import com.digi.app.user.Office;
import com.digi.app.user.repository.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("office")
public class OfficeController {

    private OfficeRepo officeRepo;

    @Autowired
    public void setOfficeRepo(OfficeRepo officeRepo) {
        this.officeRepo = officeRepo;
    }


    @GetMapping(path = "/create-page")
    public ModelAndView createpage() {
        ModelAndView model = new ModelAndView("office/create");
        model.addObject("pagetitle", "OFFICE");
        return model;
    }

    @GetMapping(path = "/view-page")
    public ModelAndView viewpage() {
        ModelAndView model = new ModelAndView("office/view");
        model.addObject("pagetitle", "OFFICE");
        return model;
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> read() {
        List<Office> list = officeRepo.findAll();
        return new ResponseEntity<>(HttpResponses.fetched(list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> read(@PathVariable int id) {
        try {
            Office staffsOffice = officeRepo.findById(id).get();
            if (staffsOffice != null) {
                return new ResponseEntity<>(HttpResponses.fetched(staffsOffice), HttpStatus.OK);
            }
        } catch (Exception e) {
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody Office staffsOffice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult);
        }
        Office savedOffice = officeRepo.save(staffsOffice);
        return new ResponseEntity<>(HttpResponses.created(savedOffice), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        if (id > 0) {
            Optional<Office> staffsOfficeOptional = officeRepo.findById(id);
            if (staffsOfficeOptional.isPresent()) {
                try {
                    officeRepo.deleteById(id);
                    return new ResponseEntity<>(HttpResponses.received(), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);

    }
}
