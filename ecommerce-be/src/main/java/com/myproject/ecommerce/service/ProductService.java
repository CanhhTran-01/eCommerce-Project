package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.ProductDetailResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.ProductMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    // get product on sale list
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductOnSaleList(){
        return productRepository.getProductOnSaleList();
    }


    // get product by category
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductsByCategory(Long categoryId){
        return productRepository.getProductByCategoryId(categoryId);
    }


    // get wish list by accountId
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getMyWishlist(Long accountId){
        return productRepository.getWishlistByAccountId(accountId);
    }


    // get product detail
    @Transactional(readOnly = true)
    public ProductDetailResponse getDetail(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductDetailResponse(product);
    }

}
