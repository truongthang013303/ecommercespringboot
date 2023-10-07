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
    public ResponseEntity<Page<Category>> getAllCatgegoriesPagi(
            @RequestParam(required = false) String sort,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer level
    )
    {
/*        if(level!=null && level!=0){
            categoryService.findAllByLevel(level, PageRequest.of(pageNumber, pageSize));
        }*/
        Page<Category> res = categoryService.getAllCatgegoriesPagi(
                sort,
                pageNumber,
                pageSize
        );

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    @GetMapping("/{parentCategoryName}/{name}")
    public ResponseEntity<Category>  findByNameAndParent(@PathVariable("parentCategoryName")String parentCategoryName, @PathVariable("name")String name){
        Category category = categoryService.findByNameAndParent(parentCategoryName, name);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
