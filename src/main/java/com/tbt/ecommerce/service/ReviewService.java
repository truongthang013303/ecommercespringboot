package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Review;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.ReviewRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);

    Page<Review> getAllRatingsSortByCreatedAtPagi(String sort, Integer pageNumber, Integer pageSize);
}
