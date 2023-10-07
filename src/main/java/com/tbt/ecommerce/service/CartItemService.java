package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.CartItemException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.CartItem;
import com.tbt.ecommerce.model.Product;
import jdk.jshell.spi.ExecutionControl;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) throws CartItemException, UserException;
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
