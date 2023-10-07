package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.CartItemException;
import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.CartItem;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException, CartItemException, UserException;

    public Cart findUserCart(Long userId) throws UserException;

}
