package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Rating;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.request.RatingRequest;
import com.tbt.ecommerce.service.RatingService;
import com.tbt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException
    {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException
    {
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
