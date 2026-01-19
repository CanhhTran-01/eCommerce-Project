package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.entity.ProductImageEntity;
import com.myproject.ecommerce.mapper.ProductImageMapper;
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
    private final ProductImageMapper productImageMapper;

    public ProductImageResponse createProductImage(ProductImageRequest productImageRequest){
        ProductImageEntity productImageEntity = productImageMapper.toEntity(productImageRequest);
        return productImageMapper.toResponse(productImageRepository.save(productImageEntity));
    }

    @Transactional(readOnly = true)
    public List<ProductImageResponse> getProductImageList(){
        return productImageRepository.findAll()
                .stream()
                .map(productImageMapper::toResponse)
                .toList();
    }

    public ProductImageResponse updateProductImage(Long id, ProductImageRequest productImageRequest){
        ProductImageEntity productImageEntity = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found!"));

        productImageMapper.update(productImageEntity, productImageRequest);
        return productImageMapper.toResponse(productImageRepository.save(productImageEntity));
    }

    public void deleteProductImage(Long id){
        productImageRepository.deleteById(id);
    }
}
