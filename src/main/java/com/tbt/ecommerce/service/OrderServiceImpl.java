package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.*;
import com.tbt.ecommerce.repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService{
    private CartRepository cartRepository;
    private CartService cartService;
    private ProductService productService;

    private OrderRepository orderRepository;

    private AddressRepository addressRepository;

    private UserRepository userRepository;

    private OrderItemRepository orderItemRepository;

    public OrderServiceImpl(CartRepository cartRepository,
                            CartService cartService,
                            ProductService productService,
                            OrderRepository orderRepository,
                            AddressRepository addressRepository,
                            UserRepository userRepository,
                            OrderItemRepository orderItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.addressRepository=addressRepository;
        this.userRepository = userRepository;
        this.orderItemRepository=orderItemRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) throws UserException {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem item : cart.getCartItems())
        {
            OrderItem orderItem = OrderItem.builder()
                    .price(item.getPrice())
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .size(item.getSize())
                    .useId(item.getUserId())
                    .discountedPrice(item.getDiscountedPrice())
                    .build();
            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreateAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for(OrderItem item : orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if(opt.isPresent())
            return opt.get();
        throw new OrderException("order not exist with id:"+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order );
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order );
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order );
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order );
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public Page<Order> getAllOrdersPagi(String sort, Integer pageNumber, Integer pageSize) {
        if(pageNumber<0) pageNumber=0;
        if(pageSize<1) pageSize=1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage;
    }
}
