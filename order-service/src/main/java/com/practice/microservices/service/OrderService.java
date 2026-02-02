package com.practice.microservices.service;

import com.practice.microservices.entity.Order;

import java.util.Optional;

public interface OrderService {

    public Optional<Order> getOrderDetailsById(String orderId);

    public Order createOrder(String orderId, String userId, Double amount, String status);
}
