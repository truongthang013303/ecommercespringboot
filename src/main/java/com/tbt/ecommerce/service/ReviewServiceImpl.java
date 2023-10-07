package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.model.Review;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.ProductRepository;
import com.tbt.ecommerce.repository.ReviewRepository;
import com.tbt.ecommerce.request.ReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService{
    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(review.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
    @Override
    public Page<Review> getAllRatingsSortByCreatedAtPagi(String sort, Integer pageNumber, Integer pageSize) {
        if(pageNumber<0) pageNumber=0;
        if(pageSize<1) pageSize=1;
        if(sort.isBlank()||sort.isEmpty()){
            sort="createdAt";
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).descending());
        Page<Review> page = reviewRepository.findAll(pageable);
        return page;
    }
}
