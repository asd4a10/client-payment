package com.example.clientpayment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse {
    private String paymentId;
    private String clientId;
    private String description;
    private int cost;
    private boolean isPaid;
}
