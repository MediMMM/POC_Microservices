package com.practice.microservices.service;

import com.practice.microservices.eventListner.PaymentFailedEvent;
import com.practice.microservices.eventListner.PaymentSuccessEvent;
import com.practice.microservices.events.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void processPayment(OrderCreatedEvent event) {
        try {
            // simulate payment
            boolean success = doPayment(event);

            if (success) {
                PaymentSuccessEvent successEvent =
                        new PaymentSuccessEvent(
                                event.getOrderId(),
                                UUID.randomUUID().toString(),
                                event.getAmount(),
                                "SUCCESS"
                        );

                kafkaTemplate.send("payment-success-topic",
                        event.getOrderId(), successEvent);

            } else {
                throw new RuntimeException("Insufficient balance");
            }

        } catch (Exception ex) {

            PaymentFailedEvent failedEvent =
                    new PaymentFailedEvent(
                            event.getOrderId(),
                            ex.getMessage(),
                            "FAILED"
                    );

            kafkaTemplate.send("payment-failed-topic",
                    event.getOrderId(), failedEvent);
        }
    }

    private boolean doPayment(OrderCreatedEvent event) {
        return event.getAmount() != null && event.getAmount() % 2 == 0;
    }
}
