package com.practice.microservices.controller;

import com.practice.microservices.DTO.Payment;
import com.practice.microservices.DTO.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order/webclient")
public class OrderWebClientController {

    private final WebClient webClient = WebClient.create("http://localhost:8081");

//    @Autowired
//    private WebClient webClient;   /get/Start

    @GetMapping("/get/by-webclient")
    public Mono<String> getByWebclinet(){

        return  webClient.get()
                .uri("/payment/get/Start")
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(String.class);
                    } else {
                        // if 404, 500, etc - directly fallback
                        return Mono.just("Payment API returned error statu  s.");
                    }
                })
                .onErrorResume(ex -> {
                    // if service DOWN, timeout, refused
                    return Mono.just("Payment API is DOWN. Fallback applied.");
                });
    }






    @GetMapping("/get/Start")
    public Mono<String> getWebData(){
        return webClient.get().uri("http://localhost:8081/payment/getStart").retrieve().bodyToMono(String.class);
    }

    @GetMapping("/{id}")
    public Mono<String> getOrder(@PathVariable String id) {
        return webClient.get()
                .uri("/payment/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .map(payment -> "Order " + id + " => " + payment);
    }

    @PostMapping
    public String createOrder(@RequestBody Map<String, Object> orderRequest) {
        String orderId = UUID.randomUUID().toString();
        Double amount = Double.valueOf(orderRequest.get("amount").toString());

        // Call Payment Service
        Payment payment = webClient.post()
                .uri("/payment")
                .bodyValue(new Payment(orderId, amount, "PENDING")) // build Payment object
                .retrieve()
                .bodyToMono(Payment.class)
                .block();

        return "Order created with ID: " + orderId + " and Payment ID: " + payment.getId();
    }

    //.bodyValue(new PaymentRequest(orderId, amount))
    //But your PaymentService expects a Payment entity in its @PostMapping:
    //@PostMapping
    //public Payment createPayment(@RequestBody Payment payment) {
    //    return service.createPayment(payment);
    //}
}
