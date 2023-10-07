package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.model.Order;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.response.ApiResponse;
import com.tbt.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> findProductByCategoryHandler(
            @RequestParam(required = false) String sort,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    )
    {
        Page<Order> res = orderService.getAllOrdersPagi(
                sort,
                pageNumber,
                pageSize
        );

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException
    {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException
    {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException
    {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException
    {
        Order order = orderService.cancledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException
    {
        orderService.deleteOrder(orderId);
        ApiResponse res= new ApiResponse();
        res.setMessage("order deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
