package com.digi.app.controller;

import com.digi.app.entity.PaymentRequest;
import com.digi.app.message.HttpResponses;
import com.digi.app.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("paymentrequests")
public class PaymentRequestController {
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    public void setPaymentRequestRepository(PaymentRequestRepository paymentRequestRepository) {
        this.paymentRequestRepository = paymentRequestRepository;
    }

    @GetMapping
    public ResponseEntity<?> getPaymentRequests() {
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findByStatus(0);
        if (!paymentRequestList.isEmpty()) {
            return new ResponseEntity<>(HttpResponses.fetched(paymentRequestList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentRequest(@NotNull @PathVariable int id) {
        Optional<PaymentRequest> paymentRequestOptional = paymentRequestRepository.findById(id);
        if (paymentRequestOptional.isPresent()) {
            return new ResponseEntity<>(HttpResponses.fetched(paymentRequestOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.BAD_REQUEST);
        }
    }
}
