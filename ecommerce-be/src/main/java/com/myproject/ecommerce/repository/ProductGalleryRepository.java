package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.ProductThumbnailImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGalleryRepository extends JpaRepository<ProductThumbnailImage, Long> {
    List<ProductThumbnailImage> findByProductId(Long productId);
}
