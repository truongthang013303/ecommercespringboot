package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category,
                                       List<String> colors,
                                       List<String> sizes,
                                       Integer minPrice,
                                       Integer maxPrice,
                                       Integer minDiscount,
                                       String sort,
                                       String stock,
                                       Integer pageNumber,
                                       Integer pageSize);
    public List<Product> findAllProducts();
}
