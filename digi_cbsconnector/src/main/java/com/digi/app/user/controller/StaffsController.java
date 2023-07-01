package com.digi.app.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.digi.app.component.CrudReturnService;
import com.digi.app.constants.MessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.exception.ValidationErrorException;
import com.digi.app.message.HttpResponses;
import com.digi.app.user.Office;
import com.digi.app.user.Staffs;
import com.digi.app.user.repository.OfficeRepo;
import com.digi.app.user.repository.StaffsRepo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("staffs")
public class StaffsController {

    private StaffsRepo staffsRepo;
    private CrudReturnService<Staffs> crudReturnService;
    private OfficeRepo officeRepo;

    private Office office;

    @Autowired
    public void setStaffsRepo(StaffsRepo staffsRepo) {
        this.staffsRepo = staffsRepo;
    }

    @Autowired
    public void setOfficeRepo(OfficeRepo officeRepo) {
        this.officeRepo = officeRepo;
    }

    @Autowired
    public void setCrudReturnService(CrudReturnService<Staffs> crudReturnService) {
        this.crudReturnService = crudReturnService;
    }

    @ModelAttribute
    public void models(Model model) {
        model.addAttribute("offices", officeRepo.findAll());
    }

    @GetMapping(path = "/create-page")
    public ModelAndView createpage() {
        ModelAndView model = new ModelAndView("staff/create");
        model.addObject("pagetitle", "STAFFS");

        return model;
    }

    @GetMapping(path = "/view-page")
    public ModelAndView viewpage() {
        ModelAndView model = new ModelAndView("staff/view");
        model.addObject("pagetitle", "STAFFS");
        model.addObject("staffs", staffsRepo.findAll());
        return model;
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> read() {
        List<Staffs> list = staffsRepo.findAll();
        ResponseEntity<?> returntype = crudReturnService.controllerReturnForList(list);
        return returntype;
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<?> read(@PathVariable String code) {
        try {
            Staffs staffs = staffsRepo.findById(code).get();
            if (staffs != null) {
                return new ResponseEntity<>(HttpResponses.fetched(staffs), HttpStatus.OK);
            }
        } catch (Exception e) {
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody Staffs staffs, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult);
        } else {
            if (staffs.getOffice() != null && officeRepo.findById(staffs.getOffice().getId()).isPresent()) {
                Staffs savedStaffs = staffsRepo.save(staffs);
                return new ResponseEntity<>(HttpResponses.created(savedStaffs), HttpStatus.CREATED);
            } else {
                throw new CustomValidationException(Collections.singletonList(MessageConstants.INVALID_OFFICE_DETAILS));
            }
        }
    }

//	@PutMapping(path="/{id}")
//	public Staffs update(@PathVariable int id,@RequestBody Staffs staffs) {
//		staffs.setId(id);
//		Staffs updatedStaffs=staffsRepo.save(staffs);
//		return updatedStaffs;
//	}

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        boolean staff_present = staffsRepo.findById(id).isPresent();

        if (staff_present) {
            staffsRepo.deleteById(id);
            return new ResponseEntity<>(HttpResponses.received(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/image", consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveImage(@RequestParam(required = true, value = "file") MultipartFile file,
                                       @RequestParam(required = true, value = "jsondata") String jsondata) {
        try {
            byte[] image_bytes = file.getBytes();

            Staffs staff_from_json = new ObjectMapper().readValue(jsondata, Staffs.class);
            String code = staff_from_json.getCode();

            Staffs tobesaved = staffsRepo.findById(code).get();
            tobesaved.setPic(image_bytes);

            Staffs savedstaff = staffsRepo.save(tobesaved);
            if (savedstaff != null) {
                return new ResponseEntity<>(HttpResponses.created(savedstaff), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
    }

    // saving excel files to the db directly
    @PostMapping("/import")
    public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        office = new Office();
        List<Staffs> tempStaffList = new ArrayList<Staffs>();

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Staffs tempStaff = new Staffs();

            XSSFRow row = worksheet.getRow(i);

            try {
                int code = (int) row.getCell(0).getNumericCellValue();
                String code_i = String.valueOf(code);
                tempStaff.setCode(code_i);

                tempStaff.setFirstName(row.getCell(1).getStringCellValue());
                tempStaff.setLastName(row.getCell(2).getStringCellValue());
                tempStaff.setGender(row.getCell(3).getStringCellValue());

                try {
                    int phone = (int) row.getCell(4).getNumericCellValue();
                    String phoneNum = String.valueOf(phone);
                    tempStaff.setPhoneNumber(phoneNum);
                } catch (Exception e) {
                    System.out.println("exception in type conversion for phone number " + e);
                }

                tempStaff.setPost(row.getCell(5).getStringCellValue());

                // setting office
                int officeId = (int) row.getCell(6).getNumericCellValue();

                office.setId(officeId);

                tempStaff.setOffice(office);

                tempStaffList.add(tempStaff);

            } catch (Exception e) {
                System.out.println("Error setting values" + e);
            }

        }

        List<Staffs> status = staffsRepo.saveAll(tempStaffList);
        workbook.close();
        if (status.size() > 0)
            return "Data Upload Successful!";

        return "Data Upload Failed!";
    }
}
