package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CategoryReorderRequest;
import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoryList() {

        var apiResponse = new ApiResponse<>(
                true,
                null,
                categoryService.getCategoryList()
        );
        return ResponseEntity.ok(apiResponse);
    }

}
