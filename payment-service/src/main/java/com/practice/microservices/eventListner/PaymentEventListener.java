package com.practice.microservices.eventListner;

import com.practice.microservices.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "payment-group"
    )
    public void consume(OrderCreatedEvent event) {
        System.out.println(" Payment Service received order: " + event.getOrderId());
        System.out.println(" Amount: " + event.getAmount());

        // TODO: initiate payment
    }
}
