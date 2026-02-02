package com.practice.microservices.repo;

import com.practice.microservices.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * FROM orders WHERE order_id = :orderId", nativeQuery = true)
    Optional<Order> getOrderById(@Param("orderId") String orderId);

//    public findByOrderId(String );
}
