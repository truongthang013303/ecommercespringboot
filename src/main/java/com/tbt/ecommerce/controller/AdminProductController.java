package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.request.CreateProductRequest;
import com.tbt.ecommerce.response.ApiResponse;
import com.tbt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProducts(@RequestBody CreateProductRequest req)throws ProductException
    {
        Product createdProduct = productService.createProduct(req);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException
    {
        productService.deleteProduct(productId);
        ApiResponse response = new ApiResponse();
        response.setMessage("product deleted successfully with id:"+productId);
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId) throws ProductException
    {
        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){
        for(CreateProductRequest request: req){
            productService.createProduct(request);
        }
        ApiResponse response = new ApiResponse();
        response.setMessage("products created successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
