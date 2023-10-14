package com.tbt.ecommerce.request;

import com.tbt.ecommerce.model.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPercent;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> sizes = new HashSet<>();
    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
}
