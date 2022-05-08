package com.example.clientpayment.service;

import com.example.clientpayment.model.PaymentRequest;
import com.example.clientpayment.model.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest);

    PaymentResponse updatePayment(PaymentRequest paymentRequest);

    void deletePaymentById(String paymentId);

    void deletePaymentsByClientId(String clientId);

    PaymentResponse getPaymentByPaymentId(String paymentId);

    Page<PaymentResponse> getPaymentsByClientId(String clientId, Pageable pageable);

    Page<PaymentResponse> getAllPayments(Pageable pageable);

    PaymentResponse makePaymentByPaymentId(String paymentId) throws JsonProcessingException;

    List<PaymentResponse> makeAllPaymentsByClientId(String clientId) throws JsonProcessingException;

    Integer totalOfAllPaymentsByClientId(String clientId);
}
