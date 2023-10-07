package com.tbt.ecommerce.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequest {
    private Long productId;
    private String review;
}
