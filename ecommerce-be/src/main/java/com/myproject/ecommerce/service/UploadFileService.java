package com.myproject.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myproject.ecommerce.entity.*;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.CategoryRepository;
import com.myproject.ecommerce.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UploadFileService {
    private final Cloudinary cloudinary;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // upload avatar for profile (only user)
    @PreAuthorize("hasRole('USER')")
    public String uploadAvatarImage(Long accountId, MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new BaseException(ErrorCode.FILE_NAME_INVALID);
        }

        String publicValue = generateValue(originalFileName);
        try {
            var result = cloudinary
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("public_id", publicValue, "folder", "avatar"));

            String url = result.get("secure_url").toString();

            Account account = accountRepository
                    .findById(accountId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

            // update image url
            User user = account.getUser();
            user.setAvatarUrl(url);

            return url;

        } catch (IOException e) {
            log.error("Upload image failed", e);
            throw new BaseException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    // upload image for category (only seller)
    @PreAuthorize("hasRole('SELLER')")
    public String uploadCategoryImage(Long categoryId, MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new BaseException(ErrorCode.FILE_NAME_INVALID);
        }

        String publicValue = generateValue(originalFileName);
        try {
            var result = cloudinary
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("public_id", publicValue, "folder", "category"));

            String url = result.get("secure_url").toString();

            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() -> new BaseException(ErrorCode.CATEGORY_NOT_FOUND));

            // update image url
            category.setCategoryImage(url);

            return url;

        } catch (IOException e) {
            log.error("Upload image failed", e);
            throw new BaseException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    // upload images for category (only seller)
    @PreAuthorize("hasRole('SELLER')")
    public void uploadProductImages(Long productId, List<MultipartFile> files) {
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        if (files == null || files.isEmpty()) {
            throw new BaseException(ErrorCode.PRODUCT_IMAGES_EMPTY);
        }

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                throw new BaseException(ErrorCode.FILE_NAME_INVALID);
            }

            String publicValue = generateValue(originalFileName);
            try {
                var result = cloudinary
                        .uploader()
                        .upload(
                                file.getBytes(),
                                ObjectUtils.asMap(
                                        "public_id", publicValue, "folder", "product/" + product.getId() + "/images"));

                String url = result.get("secure_url").toString();

                ProductThumbnailImage image = ProductThumbnailImage.builder()
                        .imageUrl(url)
                        .product(product)
                        .build();

                // update product image
                product.getProductThumbnailImageList().add(image);

            } catch (IOException e) {
                log.error("Upload image failed", e);
                throw new BaseException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }
    }

    // Generate public_id for Cloudinary
    public String generateValue(String originalName) {

        String[] parts = extractFileName(originalName);
        String fileName = parts[0].replaceAll("[^a-zA-Z0-9_-]", "_");

        return UUID.randomUUID() + "_" + fileName;
    }

    // Extract filename and extension
    public String[] extractFileName(String originalName) {

        int index = originalName.lastIndexOf(".");
        if (index == -1) {
            return new String[] {originalName, ""};
        }

        String name = originalName.substring(0, index);
        String ext = originalName.substring(index + 1);

        return new String[] {name, ext};
    }
}
