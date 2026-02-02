# POC_Microservices

A production-style microservices Proof of Concept built using Spring Boot, REST APIs, and API Gateway.
This project demonstrates real-world service-to-service communication, order & payment flow, and gateway routing.

📌 Project Overview

POC_Microservices is a microservices-based system designed to simulate a real-world backend architecture.

It contains independent services that:

Communicate via **REST**

Are **routed through an API Gateway**

Can be scaled independently

Follow clean layered architecture

**This POC is built to understand:**

> Microservices fundamentals

> API Gateway routing

> Service separation

Future-ready cloud deployment patterns

              **🧱 Architecture**

                Client
                   |
                   v
                API Gateway
                   |
                   +-------------------+
                   |                   |
                Order Service     Payment Service


**Each service is:**

Independently deployable

Loosely coupled

Exposed only via API Gateway

**🗂️ Project Structure**

              POC_Microservices/
              │
              ├── API-Gateway/
              │   └── Spring Cloud Gateway
              │
              ├── order-service/
              │   └── Handles order creation & tracking
              │
              ├── payment-service/
              │   └── Handles payment processing
              │
              └── README.md

**🔧 Tech Stack**

  Layer	Technology
  Backend	Java 17, Spring Boot
  Gateway	Spring Cloud Gateway
  API	REST
  Build Tool	Maven
  Service Communication	REST
  Config	application.yml
  Version Control	Git, GitHub
  
**📦 Microservices Details**

**🛒 Order Service**

Responsible for:

Creating orders

Fetching order details

Sending payment request to Payment Service

Base URL

http://localhost:8081/orders

💳 Payment Service

Responsible for:

Accepting payment requests

Processing payment status

Returning success/failure

Base URL

http://localhost:8082/payments

**🌐 API Gateway**

All external traffic passes through Gateway.

Route	Target Service
/api/orders/**	Order Service
/api/payments/**	Payment Service

Gateway URL

http://localhost:8080

▶️ How to Run Locally
1️⃣ Clone the Repo
git clone https://github.com/your-username/MediMMM.git
cd MediMMM

2️⃣ Start Services in Order
cd order-service
mvn spring-boot:run

cd payment-service
mvn spring-boot:run

cd API-Gateway
mvn spring-boot:run

🧪 Sample API Flow
Create Order via Gateway
POST http://localhost:8080/api/orders/create

Make Payment
POST http://localhost:8080/api/payments/pay

**🎯 Future Enhancements**

🔐 JWT Security

🧭 Eureka Service Discovery

📊 Zipkin + Sleuth tracing

📦 Dockerization

☁️ AWS EC2 / EKS deployment

🔁 Circuit Breaker (Resilience4j)

**👨‍💻 Author**

Ankit
Java Backend Developer (4.3 years)
Focused on Microservices, Cloud & Scalable Systems 🚀
