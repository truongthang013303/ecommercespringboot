package com.tbt.ecommerce.repository;

import com.tbt.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
