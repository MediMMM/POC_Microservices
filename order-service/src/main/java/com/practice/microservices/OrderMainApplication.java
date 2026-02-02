package com.practice.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
//@EnableCircuitBreaker   only for hystrix used
public class OrderMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMainApplication.class, args);
	}

	@Bean
//	@LoadBalanced  /// 🔑 this makes RestTemplate use service name resolution(need eureka server but ok)
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

//	1. What’s happening in your code
//	String url = "http://payment-service/payment/" + id;
//	String payment = restTemplate.getForObject(url, String.class);
//
//
//	Here you’re not hardcoding localhost:8081.
//
//	Instead, you’re calling by service name → http://payment-service/...
//
//	That means Spring should internally ask Eureka Discovery Server:
//			👉 "Where is payment-service running? Give me its IP/port."
//
//	But your RestTemplate right now is plain, it doesn’t know how to resolve payment-service → localhost:8081.
//	So it fails.
//
//	@Bean
//	public WebClient webClient(WebClient.Builder builder) {
//		return builder
//				.baseUrl("http://localhost:8081") // 👈 Payment Service port
//				.build();
//	}



















}
