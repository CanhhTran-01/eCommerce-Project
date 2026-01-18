package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT MAX(displayOrder) FROM CategoryEntity")
    Integer findMaxDisplayOrder();

    List<CategoryEntity> findAllByOrderByDisplayOrderAsc();
}
