package com.myproject.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UploadFileService {
    private final AccountRepository accountRepository;
    private final ProductImageRepository productImageRepository;
    private final Cloudinary cloudinary;


    // upload avatar for profile
    public String uploadAvatarImage(Long accountId, MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new BaseException(ErrorCode.FILE_NAME_INVALID);
        }

        String publicValue = generateValue(originalFileName);
        try {
            var result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicValue,
                            "folder", "avatar"
                    )
            );

            String url = (String) result.get("secure_url");

            log.info("Upload success: publicId={}, url={}", publicValue, url);

            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

            // update avatar url
            User user = account.getUser();
            user.setAvatarUrl(url);

            return url;

        } catch (IOException e) {
            log.error("Upload image failed", e);
            throw new BaseException(ErrorCode.FILE_UPLOAD_FAILED);
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
            return new String[]{originalName, ""};
        }

        String name = originalName.substring(0, index);
        String ext = originalName.substring(index + 1);

        return new String[]{name, ext};
    }
}
