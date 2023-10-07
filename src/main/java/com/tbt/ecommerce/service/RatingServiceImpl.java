package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.model.Rating;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.RatingRepository;
import com.tbt.ecommerce.request.RatingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }

    @Override
    public Page<Rating> getAllRatingsPagi(String sort, Integer pageNumber, Integer pageSize) {
        if(pageNumber<0) pageNumber=0;
        if(pageSize<1) pageSize=1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Rating> page = ratingRepository.findAll(pageable);
        return page;
    }
}
