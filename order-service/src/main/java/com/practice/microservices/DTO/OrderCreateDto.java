package com.practice.microservices.DTO;

import lombok.Data;

@Data
public class OrderCreateDto {
    private Long Id;
    private String orderId;
    private String userId;
    private Double amount;
    private String status;
}
