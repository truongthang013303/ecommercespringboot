package com.tbt.ecommerce.service;

import com.tbt.ecommerce.model.OrderItem;
import com.tbt.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }
}
