package com.practice.microservices.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallback implements Paymentclint {

    @Override
    public String getGoStart() {
        return "This is Fallback ";
    }
}
