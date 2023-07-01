package com.digi.app.repository;

import com.digi.app.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
    List<PaymentRequest> findByStatus(int status);
}
