package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT MAX(displayOrder) FROM Category")
    Integer findMaxDisplayOrder();

    List<Category> findAllByOrderByDisplayOrderAsc();
}
