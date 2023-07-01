package com.digi.app.exception;

import com.digi.app.message.HttpResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ProductExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProductNotfoundException.class)
    public ResponseEntity<Object> productNotFoundException() {
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ValidationErrorException.class)
    public ResponseEntity<Object> validationException(ValidationErrorException validationException) {
        List<FieldError> errors = validationException.bindingResult.getFieldErrors();
        List<String> errorMsg = new ArrayList<>();
        for (FieldError error : errors) {
            errorMsg.add(error.getField() + " : " + error.getDefaultMessage());
        }
        return new ResponseEntity<>(HttpResponses.validationerror(errorMsg), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<Object> customValidationException(CustomValidationException customValidationException) {
        List<String> errorMessages=customValidationException.errorMessages;
        return new ResponseEntity<>(HttpResponses.validationerror(errorMessages), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
