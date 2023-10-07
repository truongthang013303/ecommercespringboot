package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.CartItemException;
import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.CartItem;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.AddItemRequest;
import com.tbt.ecommerce.response.ApiResponse;
import com.tbt.ecommerce.service.CartService;
import com.tbt.ecommerce.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException, ExecutionControl.UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization")String jwt, @RequestBody AddItemRequest req)throws UserException, ProductException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);
        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
