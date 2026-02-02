//package com.practice.microservices.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "payment-service" , url = "http://localhost:8081")
//public interface PaymentClient {
//
//    @GetMapping("/payment/getStart")
//    String getStart();
//
//    @GetMapping("/payment/{id}")
//     String getPayment(@PathVariable("id") String id);
//
//    //for resilence
//    @GetMapping("/pay/{amount}")
//    String processPayment(@PathVariable double amount) throws InterruptedException ;
//}
