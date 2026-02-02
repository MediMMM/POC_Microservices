//package com.practice.microservices.controller;
//
//import com.practice.microservices.dto.OrderResponseDto;
//import com.practice.microservices.dto.PaymentResponseDto;
//import com.practice.microservices.entity.Payment;
//import com.practice.microservices.service.PaymentService;
//import com.practice.microservices.utility.GenericResponse;
//import org.hibernate.query.Order;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Random;
//
//@RestController
//@RequestMapping("/payment")
//public class PaymentController {
//
//    //uri : http://localhost:8081/payment/{id}
//
//    @Value("${server.port}")
//    private String port;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<GenericResponse<PaymentResponseDto>> getPayment(@PathVariable String orderId) {
//
//        GenericResponse<PaymentResponseDto> response = new GenericResponse<>();
//
//        try{
//
//            PaymentResponseDto dto = new PaymentResponseDto();
//
//            //login credentials
//
//            Payment paymentDetails = paymentService.getPaymentDetails(orderId);
//
//            if(paymentDetails==null){
//                response.setStatus(0);
//                response.setMsg("paymentDetails are null.");
//                response.setError("paymentDetails are null.");
//            }
//
//            assert paymentDetails != null;
//            dto.setAmount(paymentDetails.getAmount());
//            dto.setStatus(paymentDetails.getStatus());
//            dto.setOrderId(paymentDetails.getOrderId());
//
//            String url = "http://localhost:8082/orders/"+orderId;
//
//            OrderResponseDto orderResponseDto=restTemplate.getForObject(url , OrderResponseDto.class);
//
//            assert orderResponseDto != null;
//            dto.setUserId(orderResponseDto.getUserId());
//
//            response.setData(dto);
//            response.setStatus(1);
//            response.setMsg("data fetched successfully !!");
//
//
//        } catch (Exception e) {
//            response.setStatus(0);
//            response.setMsg("data not fetched successfully !!");
//            response.setError("unsuccessfull");
//            throw new RuntimeException(e);
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}
