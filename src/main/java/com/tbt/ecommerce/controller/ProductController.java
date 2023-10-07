package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize
            )
    {
            Page<Product> res = productService.getAllProduct(
                    category,
                    color,
                    size,
                    minPrice,
                    maxPrice,
                    minDiscount,
                    sort,
                    stock,
                    pageNumber,
                    pageSize
            );

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProducByIdHandler(@PathVariable Long productId) throws ProductException
    {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
//        List<Product> products = productService.searchProduct(q);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

}
