package com.example.clientpayment.controller;

import com.example.clientpayment.model.PaymentRequest;
import com.example.clientpayment.model.PaymentResponse;
import com.example.clientpayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }

    @PutMapping
        public PaymentResponse updatePayment(@RequestParam String paymentId,
                                             @RequestBody PaymentRequest paymentRequest){
        paymentRequest.setPaymentId(paymentId);
        return paymentService.updatePayment(paymentRequest);
    }

    @GetMapping
    public PaymentResponse getPaymentByPaymentId(@RequestParam String paymentId) {
        return paymentService.getPaymentByPaymentId(paymentId);
    }

    @GetMapping("/client")
    public Page<PaymentResponse> getPaymentsByClientId(@RequestParam String clientId, Pageable pageable) {
        return paymentService.getPaymentsByClientId(clientId, pageable);
    }

    @DeleteMapping
    public String deletePaymentByPaymentId(@RequestParam String paymentId) {
        paymentService.deletePaymentById(paymentId);
        return "Deleted by Payment Id";
    }

    @DeleteMapping("/client")
    public String deletePaymentsByClientId(@RequestParam String clientId) {
        paymentService.deletePaymentsByClientId(clientId);
        return "Deleted by Client Id";
    }

    @GetMapping("/all")
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentService.getAllPayments(pageable);
    }

    @PutMapping("/pay")
    public PaymentResponse makePaymentByPaymentId(@RequestParam String paymentId){
        return paymentService.makePaymentByPaymentId(paymentId);
    }

    @PutMapping("/pay/all")
    public Page<PaymentResponse> makeAllPaymentsByClientId(@RequestParam String clientId, Pageable pageable){
        return paymentService.makeAllPaymentsByClientId(clientId, pageable);
    }

    @GetMapping("/client/total")
    public int getTotalByClientId(@RequestParam String clientId) {
        return paymentService.totalOfAllPaymentsByClientId(clientId);
    }
}
