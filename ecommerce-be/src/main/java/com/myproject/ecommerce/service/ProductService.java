package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductDetailResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.ProductMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.utils.ProductCodeMakingUtils;
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


    public ProductSummaryResponse createProduct(ProductRequest productRequest){
        Product product = productMapper.toEntity(productRequest);

        // set product code
        product.setProductCode(ProductCodeMakingUtils.generateProductCode());

        return productMapper.toProductSummaryResponse(productRepository.save(product));
    }


    // get product on sale list
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductOnSaleList(){
        return productRepository.getProductOnSaleList()
                .stream()
                .map(productMapper::toProductSummaryResponse)
                .toList();
    }


    // get product by category
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductsByCategory(Long categoryId){
        return productRepository.getProductByCategoryId(categoryId)
                .stream()
                .map(productMapper::toProductSummaryResponse)
                .toList();
    }


    // get wish list
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getMyWishlist(Long accountId){
        return productRepository.getWishlistByAccountId(accountId)
                .stream()
                .map(productMapper::toProductSummaryResponse)
                .toList();
    }


    // get product detail
    @Transactional(readOnly = true)
    public ProductDetailResponse getDetail(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductDetailResponse(product);
    }

}
