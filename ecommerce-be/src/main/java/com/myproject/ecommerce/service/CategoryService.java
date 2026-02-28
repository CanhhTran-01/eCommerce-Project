package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.mapper.CategoryMapper;
import com.myproject.ecommerce.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // get list category
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
