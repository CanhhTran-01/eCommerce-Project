package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.entity.ProductImageEntity;
import com.myproject.ecommerce.mapper.ProductEntityMapper;
import com.myproject.ecommerce.mapper.ProductImageEntityMapper;
import com.myproject.ecommerce.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductImageEntityMapper productImageEntityMapper;

    public ProductImageResponse createProductImage(ProductImageRequest productImageRequest){
        ProductImageEntity productImageEntity = productImageEntityMapper.toEntity(productImageRequest);
        return productImageEntityMapper.toResponse(productImageRepository.save(productImageEntity));
    }

    @Transactional(readOnly = true)
    public List<ProductImageResponse> getProductImageList(){
        return productImageRepository.findAll()
                .stream()
                .map(productImageEntityMapper::toResponse)
                .toList();
    }

    public ProductImageResponse updateProductImage(Long id, ProductImageRequest productImageRequest){
        ProductImageEntity productImageEntity = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found!"));

        productImageEntityMapper.update(productImageEntity, productImageRequest);
        return productImageEntityMapper.toResponse(productImageRepository.save(productImageEntity));
    }

    public void deleteProductImage(Long id){
        productImageRepository.deleteById(id);
    }
}
