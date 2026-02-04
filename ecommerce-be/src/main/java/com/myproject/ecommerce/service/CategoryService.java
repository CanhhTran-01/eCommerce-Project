package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.entity.Category;
import com.myproject.ecommerce.mapper.CategoryMapper;
import com.myproject.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // tạo mới 1 category
    public CategoryResponse createCategory(CategoryRequest categoryRequest){
        Category category = categoryMapper.toEntity(categoryRequest);

        // set tay
        Integer maxOrder = categoryRepository.findMaxDisplayOrder();
        category.setDisplayOrder(maxOrder == null ? 1 : maxOrder+1);

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    // get list category
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

}
