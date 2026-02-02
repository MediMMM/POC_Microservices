package com.practice.microservices.serviceimpl;

import com.practice.microservices.entity.Order;
import com.practice.microservices.events.OrderCreatedEvent;
import com.practice.microservices.events.PaymentFailedEvent;
import com.practice.microservices.events.PaymentSuccessEvent;
import com.practice.microservices.repo.OrderRepo;
import com.practice.microservices.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
public class OrderServiceimpl implements OrderService {

    Logger logger = Logger.getLogger(String.valueOf(OrderServiceimpl.class));



    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public Optional<Order> getOrderDetailsById(String orderId) {
        return orderRepo.getOrderById(orderId);
    }

    @Override
    public Order createOrder(String orderId, String userId, Double amount, String status) {

        Order order = new Order();
        order.setOrderId(orderId);
        order.setAmount(amount);
        order.setUserId(userId);
        order.setStatus(status);

        Order savedOrder = orderRepo.save(order);
        if(savedOrder==null){
            throw new RuntimeException("nothng saved");
        }

        OrderCreatedEvent event = new OrderCreatedEvent(orderId, amount);  // create event

        try {
            kafkaTemplate.send("order-created-topic", orderId, event); // created an event for order creation
            logger.info("Order event published");
        } catch (Exception ex) {
            logger.warning("Kafka publish failed, but order saved: " + ex.getMessage());
        }


        return savedOrder;
    }

    @KafkaListener(topics = "payment-success-topic", groupId = "order-group")
    @Transactional
    public void handlePaymentSuccess(PaymentSuccessEvent event) {

        Order order=orderRepo.getOrderById(event.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("CONFIRMED");
        order.setPaymentId(event.getPaymentId());

        orderRepo.save(order);

        System.out.println("Order confirmed: " + event.getOrderId());
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "order-group")
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent event) {

//        Order order = orderRepo.findById(Long.valueOf(event.getOrderId()))
//                .orElseThrow(() -> new RuntimeException("Order not found"));

        Order order=orderRepo.getOrderById(event.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("CANCELLED");
        order.setFailureReason(event.getReason());

        orderRepo.save(order);

        System.out.println("Order cancelled: " + event.getOrderId());
    }



}
