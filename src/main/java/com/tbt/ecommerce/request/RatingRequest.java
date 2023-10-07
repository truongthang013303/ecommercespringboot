package com.tbt.ecommerce.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingRequest {
    private Long productId;
    private double rating;

}
