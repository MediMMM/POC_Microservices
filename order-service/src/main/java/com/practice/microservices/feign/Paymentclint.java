package com.practice.microservices.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment-service", url = "http://localhost:8081" , fallback = PaymentFallback.class)
public interface Paymentclint {

    @GetMapping("/payment/get/Start")
    public String getGoStart();
}
