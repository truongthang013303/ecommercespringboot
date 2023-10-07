package com.tbt.ecommerce.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddItemRequest {
    private Long productId;
    private String size;
    private int quantity;
    private Integer price;
}
