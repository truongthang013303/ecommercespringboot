package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Address;
import com.tbt.ecommerce.model.Order;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.service.OrderService;
import com.tbt.ecommerce.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Address shippingAddress
            )throws UserException
    {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException
    {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@RequestHeader("Authorization")String jwt, @PathVariable("id")Long orderId)throws UserException, OrderException
    {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
