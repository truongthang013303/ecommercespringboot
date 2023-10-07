package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.CartItemException;
import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.CartItem;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.AddItemRequest;
import com.tbt.ecommerce.response.ApiResponse;
import com.tbt.ecommerce.service.CartItemService;
import com.tbt.ecommerce.service.CartService;
import com.tbt.ecommerce.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

//    @PutMapping("/add")
//    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException, CartItemException, ExecutionControl.UserException {
//        User user = userService.findUserProfileByJwt(jwt);
//        cartService.addCartItem(user.getId(), req);
//        ApiResponse res= new ApiResponse();
//        res.setMessage("item added to cart");
//        res.setStatus(true);
//        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
//    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody CartItem cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization")String jwt
    )throws UserException, CartItemException
    {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization")String jwt
    )throws UserException, CartItemException
    {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res=  new ApiResponse();
        res.setMessage("item deleted from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
