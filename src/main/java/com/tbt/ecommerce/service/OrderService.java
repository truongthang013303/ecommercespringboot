package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Address;
import com.tbt.ecommerce.model.Order;
import com.tbt.ecommerce.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws UserException;
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> usersOrderHistory(Long orderId);
    public Order placedOrder(Long orderId)throws OrderException;
    public Order confirmedOrder(Long orderId)throws OrderException;
    public Order shippedOrder(Long orderId)throws OrderException;
    public Order deliveredOrder(Long orderId)throws OrderException;
    public Order cancledOrder(Long orderId)throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;

    Page<Order> getAllOrdersPagi(
            String sort,
            Integer pageNumber,
            Integer pageSize
            );

}
