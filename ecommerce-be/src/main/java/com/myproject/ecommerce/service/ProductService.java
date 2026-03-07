package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductFilterSearchRequest;
import com.myproject.ecommerce.dto.response.ProductDetailResponse;
import com.myproject.ecommerce.dto.response.ProductSuggestionResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.dto.response.SuggestionResponse;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.mapper.ProductMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSuggestionService productSuggestionService;

    // get product on sale list
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductOnSaleList() {
        return productRepository.getProductOnSaleList();
    }

    // get product by category
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getProductsByCategory(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    // get wish list by accountId
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getMyWishlist(Long accountId) {
        return productRepository.getWishlistByAccountId(accountId);
    }

    // get product detail
    @Transactional(readOnly = true)
    public ProductDetailResponse getDetail(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductDetailResponse(product);
    }

    // get related product (same category)
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getRelatedProducts(Long productId) {
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        return productRepository.getProductsByCategoryId(product.getCategory().getId());
    }

    // filter product
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getFilterSearchProduct(ProductFilterSearchRequest request) {
        return productRepository.searchbyFilter(request);
    }

    // check wishlisted by user
    public boolean isWishListed(Long productId, Long accountId) {
        return productRepository.existsByIdAndWishedByAccountId(productId, accountId);
    }

    // get keyword and product suggestion
    public SuggestionResponse getTextsAndProductsSuggestion(String text) {

        // insert keywords for later searching by someone
        productSuggestionService.insertTextForSuggestion(text);

        // get keywords suggestion
        List<String> texts = productSuggestionService.getSuggestionTexts(text);

        // get products suggestion
        List<ProductSuggestionResponse> data = productRepository.suggestByName(text);

        return SuggestionResponse.builder()
                .suggestionTexts(texts)
                .productSuggestionResponses(data)
                .build();
    }
}
