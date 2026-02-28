package com.myproject.ecommerce.service;

import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // insert Product to user's wishlist
    @Transactional
    public void insertToWishList(Long accountId, Long productId) {
        User user = userRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Product product =
                productRepository.findById(productId).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        if (user.getWishList().contains(product)) {
            throw new BaseException(ErrorCode.FAV_PRODUCT_EXISTED);
        }

        user.getWishList().add(product);
        userRepository.save(user);
    }
}
