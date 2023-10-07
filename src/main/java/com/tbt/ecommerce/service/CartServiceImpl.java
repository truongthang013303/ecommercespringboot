package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.CartItemException;
import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.CartItem;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.CartRepository;
import com.tbt.ecommerce.request.AddItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CartServiceImpl implements CartService {
    public CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    @Autowired
    private UserService userService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException, CartItemException, UserException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if(isPresent==null)
        {
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .cart(cart)
                    .quantity(req.getQuantity())
                    .userId(userId)
                    .build();
            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }

        return "Item Add To Cart";
    }

    @Override
    public Cart findUserCart(Long userId) throws UserException{
        Cart cart = cartRepository.findByUserId(userId);
        if(cart==null) {
            cart = createCart(userService.findUserById(userId));
        }else {
            int totalPrice =0;
            int totalDiscountedPrice=0;
            int totalItem=0;
            if(cart.getCartItems()!=null){
                for(CartItem cartItem: cart.getCartItems())
                {
                    totalPrice+=cartItem.getPrice();
                    totalDiscountedPrice+=cartItem.getDiscountedPrice();
                    totalItem+=cartItem.getQuantity();
                }
            }
            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItem(totalItem);
            cart.setTotalPrice(totalPrice);
            cart.setDiscounte(totalPrice-totalDiscountedPrice);
        }
        return cartRepository.save(cart);
    }
}
