package com.tbt.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;
    private String size;
    private int quantity;
    private Integer price;

    @Column(name = "discoundted_price")
    private Integer discountedPrice;

    private Long userId;
}
