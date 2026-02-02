package com.practice.microservices.DTO;

import lombok.Data;

@Data
public class UpdateOrderDto {

    private Long orderid;
    private Long userid;
    private String status;
    private Double amount;
}
