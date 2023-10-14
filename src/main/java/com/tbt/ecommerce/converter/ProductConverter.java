package com.tbt.ecommerce.converter;

import com.tbt.ecommerce.model.Product;

public class ProductConverter {
    public static Product mergeProduct(Product in, Product out){
        out.setTitle(in.getTitle());
        out.setImageUrl(in.getImageUrl());
//        out.setQuantity(in.getQuantity());
        out.setQuantity(in.getSizes().stream().mapToInt(s->s.getQuantity()).reduce(0, (a, b) -> a + b));
        out.setColor(in.getColor());
        out.setDescription(in.getDescription());
        out.setId(in.getId());
        out.setBrand(in.getBrand());
        out.setCategory(in.getCategory());
        out.setPrice(in.getPrice());
        out.setDiscountedPrice(in.getDiscountedPrice());
        out.setDiscountPercent(in.getDiscountPercent());
        out.setNumRatings(in.getNumRatings());
        out.setCreatedAt(in.getCreatedAt());
        out.setSizes(in.getSizes());
//        if(!in.getRatings().isEmpty()){
//            out.setRatings(in.getRatings());
//        }
//        if(!in.getReviews().isEmpty()){
//            out.setReviews(in.getReviews());
//        }
        return out;
    }
}
