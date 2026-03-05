package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AddToCartRequest;
import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.CartItemRedis;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.utils.CurrentProductPriceUtils;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    // add items to cart
    public void addItemToCart(Long accountId, AddToCartRequest request) {

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        Long userId = account.getUser().getId();
        String cartKey = buildCartKey(userId); // redis key -> cart:userId

        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();

        String cartField = request.getProductId().toString(); // redis field -> productId

        CartItemRedis cartItemRedis = CartItemRedis.builder()
                .productId(request.getProductId())
                .priceAtAdd(CurrentProductPriceUtils.getCurrentPrice(product.getPrice(), product.getDiscountPrice()))
                .build();

        hashOps.put(cartKey, cartField, cartItemRedis); // save in Redis
        redisTemplate.expire(cartKey, Duration.ofDays(7)); // set TTL  (7 days)
    }

    // delete item from cart
    public void deleteItemFromCart(Long accountId, Long productId) {

        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        Long userId = account.getUser().getId();
        String cartKey = buildCartKey(userId);

        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();

        Long deleted = hashOps.delete(cartKey, productId.toString());

        if (deleted == 0) {
            throw new BaseException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
    }

    // get cart items
    public List<CartItemResponse> getCartItems(Long accountId) {
        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        Long userId = account.getUser().getId();
        String cartKey = buildCartKey(userId);

        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> cartItemRedisEntries = hashOps.entries(cartKey);

        if (cartItemRedisEntries.isEmpty()) {
            return Collections.emptyList();
        }

        List<CartItemResponse> data = new ArrayList<>();

        for (Object value : cartItemRedisEntries.values()) {

            CartItemRedis cartItemRedis = (CartItemRedis) value;

            Product product = productRepository
                    .findById(cartItemRedis.getProductId())
                    .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

            CartItemResponse response = CartItemResponse.builder()
                    .productId(product.getId())
                    .mainImageUrl(product.getMainImageUrl())
                    .productName(product.getProductName())
                    .quantity(1)
                    .price(CurrentProductPriceUtils.getCurrentPrice(product.getPrice(), product.getDiscountPrice()))
                    .checked(false)
                    .build();

            data.add(response);
        }

        return data;
    }

    private String buildCartKey(Long userId) {
        return "cart:" + userId;
    }
}
