package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.ThumbnailImageResponse;
import com.myproject.ecommerce.mapper.ProductGalleryMapper;
import com.myproject.ecommerce.repository.ProductGalleryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGalleryService {
    private final ProductGalleryRepository productGalleryRepository;
    private final ProductGalleryMapper productGalleryMapper;

    // get product gallery
    @Transactional(readOnly = true)
    public List<ThumbnailImageResponse> getProductGallery(Long productId) {
        return productGalleryRepository.findByProductId(productId).stream()
                .map(productGalleryMapper::toThumbnailImageResponse)
                .toList();
    }
}
