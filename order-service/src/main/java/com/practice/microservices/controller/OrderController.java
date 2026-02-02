package com.practice.microservices.controller;

import com.practice.microservices.DTO.OrderCreateDto;
import com.practice.microservices.entity.Order;
import com.practice.microservices.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateDto requestDto){

        try{

            if(requestDto == null){
                throw new RuntimeException("request body is null ");
            }

            String orderId = requestDto.getOrderId();
            Double amount = requestDto.getAmount();
            String userId = requestDto.getUserId();
            String status = requestDto.getStatus();

            Order order = orderService.createOrder(orderId, userId, amount, status);
            if( order == null){
                throw new RuntimeException("order is saved null ");
            }

            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
