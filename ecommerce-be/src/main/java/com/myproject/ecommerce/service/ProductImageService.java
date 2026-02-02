package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.entity.ProductImage;
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
        ProductImage productImage = productImageMapper.toEntity(productImageRequest);
        return productImageMapper.toResponse(productImageRepository.save(productImage));
    }

    @Transactional(readOnly = true)
    public List<ProductImageResponse> getProductImageList(){
        return productImageRepository.findAll()
                .stream()
                .map(productImageMapper::toResponse)
                .toList();
    }

    public ProductImageResponse updateProductImage(Long id, ProductImageRequest productImageRequest){
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found!"));

        productImageMapper.update(productImage, productImageRequest);
        return productImageMapper.toResponse(productImageRepository.save(productImage));
    }

    public void deleteProductImage(Long id){
        productImageRepository.deleteById(id);
    }
}
