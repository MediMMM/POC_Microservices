package com.practice.microservices.controller;

import com.netflix.discovery.converters.Auto;
import com.practice.microservices.DTO.Payment;
import com.practice.microservices.DTO.PaymentRequest;
import com.practice.microservices.DTO.UpdateOrderDto;
import com.practice.microservices.DTO.UpdateOrderRequestDto;
import com.practice.microservices.entity.Order;
import com.practice.microservices.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order/rest")
public class OrderRestTemplateController {

    // payment service : 8081 :::http://localhost:8081/payment/{id}/getStart

    //order service : 8082

    // then for calling from order to payment

    //http://localhost:8082/order/rest/{id}   like normal api hit(internally it will call other)


//    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/update/order/{orderId}")
    public ResponseEntity<UpdateOrderDto> updateOrder(@PathVariable String orderId
                                           , @RequestBody UpdateOrderRequestDto requestDto){

        UpdateOrderDto dto = new UpdateOrderDto();
        try{
            //user validation check

            //permission check

            if( orderId ==null){
                throw new RuntimeException("orderid is null");
            }

            //fetch data
            Optional<Order> orderOpt = orderService.getOrderDetailsById(orderId);

            //set data
            dto.setAmount(requestDto.getAmount());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //response back
        return new ResponseEntity<>(dto , HttpStatus.OK);
    }









        @CircuitBreaker(name = "paymentCB", fallbackMethod = "paymentFallback")
        @GetMapping("/get/go-by-rest")
        public String getGoByRest(){

            String url = "http://localhost:8081/payment/get/Start";

            String res =restTemplate.getForObject(url, String.class);

            return res;

        }


        public String paymentFallback(Throwable ex) {
            return "Fallback: Payment Service is down!";
        }


    @GetMapping("/order/getStart")
    public String getOrderStart(){

        String url = "http://localhost:8081/payment/getStart";

        String res = restTemplate.getForObject(url, String.class);

        return  res;

    }



    //1 : getForObject
    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {
        String url = "http://localhost:8081/payment/" + id;
        // String url = "http://payment-service/payment/" + id; (we need eureka server to use this + @loadbalanced)
        String payment = restTemplate.getForObject(url, String.class);
        return "Order " + id + " => " + payment;
    }

    //2: getforEntity
    @GetMapping("/entity/{id}")
    public String getOrderbyentity(@PathVariable String id) {
        String url = "http://localhost:8081/payment/" + id;

        // Yeh pura ResponseEntity return karega (status, headers, body)
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Tujhe body chahiye to getBody() use kar
        String payment = response.getBody();

        // Status aur headers bhi access kar sakta hai
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        HttpHeaders headers = response.getHeaders();

        return "Order " + id + " => " + payment + " (status: " + statusCode + ")"+" (headers: " + headers+ ")";
    }

    @PostMapping
    public String createOrder(@RequestBody Map<String, Object> orderRequest) {
        String orderId = UUID.randomUUID().toString();
        Double amount = Double.valueOf(orderRequest.get("amount").toString());

        // Prepare request for Payment service
//        PaymentRequest paymentRequest = new PaymentRequest(orderId, amount);

        Payment payment= new Payment("102",500.00,"pending");

        // Call Payment Service (POST)
        Payment payments = restTemplate.postForObject(
                "http://localhost:8081/payment",  // Payment service URL
                payment,                   // request body
                Payment.class                     // response type
        );

        assert payments != null;
        return "Order created with ID: " + orderId + " and Payment ID: " + payments.getId();
    }


    /*🔹 How does it work?

        You create a RestTemplate bean in your Spring app.

        Then inject (@Autowired) it wherever you want to call another service.

        With it you can do:

        GET → Fetch data from another service.

        POST → Send data to another service.

        PUT → Update something in another service.

        DELETE → Remove something in another service.


        🔹 Difference between getForObject & getForEntity

        getForObject → Just gives you the body (response content).

        getForEntity → Gives you the full response (status code, headers, and body).

        So if you only care about the result → use getForObject.
        If you want to check HTTP status also → use getForEntity.



        🔹 Things to Know for Interviews / Real Work

        It’s Synchronous → Your service waits until the other service replies (like making a phone call and waiting for the answer).

        Deprecation Notice → RestTemplate is still usable but Spring says WebClient (from Spring WebFlux) is the new modern way (asynchronous + reactive).

        Load Balancing → With Spring Cloud, you can use @LoadBalanced on RestTemplate to make calls using service names (http://payment-service/...) instead of hardcoding URLs (http://localhost:8081/...).

        Error Handling → By default, if the other service returns 4xx or 5xx, RestTemplate throws an exception (RestClientException). So you should handle it with try/catch.



        */

}
