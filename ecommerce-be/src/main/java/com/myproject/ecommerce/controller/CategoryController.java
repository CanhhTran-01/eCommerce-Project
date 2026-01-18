package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CategoryReorderRequest;
import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/api/categories")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PutMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> reorderCategories(@RequestBody CategoryReorderRequest
                                                                            categoryReorderRequest){

        return ResponseEntity.ok(categoryService.reorderCategoryList(categoryReorderRequest));
    }

    @DeleteMapping("/api/categories/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ("Deleted category with id: " + id);
    }

}
