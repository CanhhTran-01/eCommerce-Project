package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductResponse;
import com.myproject.ecommerce.entity.ProductEntity;
import com.myproject.ecommerce.mapper.ProductMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.utils.ProductCodeMakingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest productRequest){
        ProductEntity productEntity = productMapper.toEntity(productRequest);

        productEntity.setProductCode(ProductCodeMakingUtils.generateProductCode());

        return productMapper.toResponse(productRepository.save(productEntity));
    }

    public ProductResponse updateProject(Long id, ProductRequest productRequest){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        productMapper.update(productEntity, productRequest);
        return productMapper.toResponse(productRepository.save(productEntity));
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
