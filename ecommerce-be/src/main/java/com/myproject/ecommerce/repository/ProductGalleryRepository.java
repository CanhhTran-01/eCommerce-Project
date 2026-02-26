package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.ProductThumbnailImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGalleryRepository extends JpaRepository<ProductThumbnailImage, Long> {
    List<ProductThumbnailImage> findByProductId(Long productId);
}
