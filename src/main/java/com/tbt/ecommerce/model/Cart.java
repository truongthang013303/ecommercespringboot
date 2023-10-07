package com.tbt.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<CartItem> cartItems = new HashSet<>();
    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "totalItem")
    private int totalItem;

    private int totalDiscountedPrice;

    private int discounte;
}
