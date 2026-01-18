package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductResponse;
import com.myproject.ecommerce.entity.ProductEntity;
import com.myproject.ecommerce.mapper.ProductEntityMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.utils.ProductCodeMakingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    public ProductResponse createProduct(ProductRequest productRequest){
        ProductEntity productEntity = productEntityMapper.toEntity(productRequest);

        productEntity.setProductCode(ProductCodeMakingUtils.generateProductCode());

        return productEntityMapper.toResponse(productRepository.save(productEntity));
    }

    public ProductResponse updateProject(Long id, ProductRequest productRequest){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        productEntityMapper.update(productEntity, productRequest);
        return productEntityMapper.toResponse(productRepository.save(productEntity));
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
