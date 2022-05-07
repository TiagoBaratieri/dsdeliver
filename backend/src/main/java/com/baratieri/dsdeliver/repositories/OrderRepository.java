package com.baratieri.dsdeliver.repositories;

import com.baratieri.dsdeliver.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
