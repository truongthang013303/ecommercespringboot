package com.tbt.ecommerce.repository;

import com.tbt.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("name")String name, @Param("parentCategoryName")String parentCategoryName);

    public Page<Category> findAllByLevel(Integer level, Pageable pageable);
    @Query("SELECT c FROM Category c WHERE c.parentCategory.id=:parentCategoryId")
    public Page<Category> findChirldrenCategory(@Param("parentCategoryId")Integer parentCategoryId, Pageable pageable);
}
