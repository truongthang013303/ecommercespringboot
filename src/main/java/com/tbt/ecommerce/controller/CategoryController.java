package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.model.Category;
import com.tbt.ecommerce.model.Order;
import com.tbt.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>>  getAllCategories(){
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.ACCEPTED);
    }
    @GetMapping
    public ResponseEntity<Page<Category>> filterCategories(
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0")  Integer pageNumber,
            @RequestParam(defaultValue = "1")  Integer pageSize,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) Integer parentCategoryId
    )
    {
        Page<Category> res = categoryService.filterCategory(sort, pageNumber, pageSize, parentCategoryId, level);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    @GetMapping("/{parentCategoryName}/{name}")
    public ResponseEntity<Category>  findByNameAndParent(@PathVariable("parentCategoryName")String parentCategoryName, @PathVariable("name")String name){
        Category category = categoryService.findByNameAndParent(parentCategoryName, name);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
