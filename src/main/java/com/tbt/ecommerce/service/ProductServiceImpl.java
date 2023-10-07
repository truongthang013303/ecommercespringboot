package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.ProductException;
import com.tbt.ecommerce.model.Category;
import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.repository.CategoryRepository;
import com.tbt.ecommerce.repository.ProductRepository;
import com.tbt.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService=userService;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        if(topLevel==null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel=categoryRepository.save(topLevelCategory);

        }
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
        if(secondLevel==null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel=categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
        if(thirdLevel==null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel=categoryRepository.save(thirdLevelCategory);
        }

        Product product = Product.builder()
                .title(req.getTitle())
                .color(req.getColor())
                .description(req.getDescription())
                .discountedPrice(req.getDiscountedPrice())
                .imageUrl(req.getImageUrl())
                .brand(req.getBrand())
                .price(req.getPrice())
                .sizes(req.getSize())
                .quantity(req.getQuantity())
                .category(thirdLevel)
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        product.setQuantity(0);
        productRepository.save(product);
        return "Product id:"+productId+"has been deleted";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id:"+productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        if(pageSize==null){
            pageSize=1;
        }
        if(pageNumber==null){
            pageNumber=0;
        }
        if(pageSize<1){
            pageSize=1;
        }
        if(pageNumber<0){
            pageNumber=0;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        if(colors!=null){
            if(!colors.isEmpty()){
                products=products.stream().filter(p->colors.stream().anyMatch(
                                c->c.equalsIgnoreCase(p.getColor())
                        )
                ).collect(Collectors.toList());
            }
        }

        if(stock!=null){
            if(stock.equals("in_stock")){
                products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }else if(stock.equals("out_of_stock")){
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex=(int)pageable.getOffset();
        int endIndex=Math.min(startIndex+pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }
}
