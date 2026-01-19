package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CategoryReorderItem;
import com.myproject.ecommerce.dto.request.CategoryReorderRequest;
import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.entity.CategoryEntity;
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
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryRequest);

        // set tay
        Integer maxOrder = categoryRepository.findMaxDisplayOrder();
        categoryEntity.setDisplayOrder(maxOrder == null ? 1 : maxOrder+1);

        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    // lấy ra category list
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryList(){
        return categoryRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    // Reorder category list
    public List<CategoryResponse> reorderCategoryList(CategoryReorderRequest categoryReorderRequest){

        for (CategoryReorderItem item : categoryReorderRequest.getCategoryReorderRequestList()){

            CategoryEntity categoryEntity = categoryRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            categoryEntity.setDisplayOrder(item.getDisplayOrder());

        }
        return categoryRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    // delete category
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
