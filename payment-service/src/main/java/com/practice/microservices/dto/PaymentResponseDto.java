package com.practice.microservices.dto;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private String orderId;
    private Double amount;
    private String status;
    private String userId;
}
