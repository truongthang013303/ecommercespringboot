package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Rating;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.RatingRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user)throws ProductException;
    public List<Rating> getProductsRating(Long productId);


    Page<Rating> getAllRatingsPagi(String sort, Integer pageNumber, Integer pageSize);
}
