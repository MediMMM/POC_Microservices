package com.practice.microservices.feign;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order/feign")
public class OrderFeignController {

//    private final PaymentClient paymentClient;
//
//    public OrderFeignController(PaymentClient paymentClient) {
//        this.paymentClient = paymentClient;
//    }

//    @Autowired
//    private PaymentClient paymentClient;

    @Autowired
    private Paymentclint paymentclint;

    @GetMapping("/get/by-client")
    public String getByFeign() {

        String res = paymentclint.getGoStart();
        return res;

    }


//
//    @GetMapping("/get/Start")
//    public String getdata(){
//        String sd =paymentClient.getStart();
//
//        return sd;
//    }
//
//    @GetMapping("/{id}")
//    public String getOrder(@PathVariable String id) {
//        String payment = paymentClient.getPayment(id);
//        System.out.println("Order Service got response: " + payment);
//        return "Order " + id + " => " + payment;
//    }
//
//
//
//    @GetMapping("/order/pay/{amount}")
//    @CircuitBreaker(name = "paymentServiceCB", fallbackMethod = "paymentFallback")
//    @Retry(name = "paymentServiceRetry")
//    @TimeLimiter(name = "paymentServiceTL")
//    @RateLimiter(name = "paymentServiceRL")
//    @Bulkhead(name = "paymentServiceBH")
//    public CompletableFuture<String> pay(@PathVariable double amount) {
//        // wrap Feign call into CompletableFuture for @TimeLimiter
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                return paymentClient.processPayment(amount);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    // Fallback method
//    public CompletableFuture<String> paymentFallback(double amount, Throwable ex) {
//        return CompletableFuture.completedFuture(
//                "Payment service is currently unavailable. Please try later!"
//        );
//    }
//}

}