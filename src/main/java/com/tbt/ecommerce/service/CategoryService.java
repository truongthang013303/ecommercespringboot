package com.tbt.ecommerce.service;

import com.tbt.ecommerce.model.Category;
import com.tbt.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryService {
    public Category findByNameAndParent(String name, String parentCategoryName);

    public List<Category> findAllCategories();

    Page<Category> getAllCatgegoriesPagi(String sort, Integer pageNumber, Integer pageSize);

    Page<Category> filterCategory(String sort, Integer pageNumber, Integer pageSize, Integer parentCategoryId, Integer level);

    public Page<Category> findAllByLevel(Integer level, Pageable pageable);

    Page<Category> findChirldrenCategory(Integer parentCategoryId, Pageable pageable);
}
