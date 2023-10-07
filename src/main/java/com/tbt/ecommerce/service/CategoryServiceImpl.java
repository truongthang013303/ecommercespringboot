package com.tbt.ecommerce.service;

import com.tbt.ecommerce.model.Category;
import com.tbt.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category findByNameAndParent(String name, String parentCategoryName) {
        return categoryRepository.findByNameAndParent(name, parentCategoryName);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCatgegoriesPagi(String sort, Integer pageNumber, Integer pageSize) {
        if(pageNumber<0) pageNumber=0;
        if(pageSize<1) pageSize=1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        return page;
    }

    @Override
    public Page<Category> findAllByLevel(Integer level, Pageable pageable) {
        if(level==null) level=1;
        if(pageable==null) pageable = PageRequest.of(0,1, Sort.by("id").descending());
        return categoryRepository.findAllByLevel(level, pageable);
    }
}
