package com.practice.microservices.repo;

import com.practice.microservices.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select * from payments p where p.order_id = :orderId", nativeQuery = true)
    public Payment getPaymentDetailsByOrder(String orderId);

}
