package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.mapper.ProductMapper;
import com.myproject.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductSuggestionService productSuggestionService;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductOnSaleList_returnList() {
        // ARRANGE
        ProductSummaryResponse product1 = ProductSummaryResponse.builder()
                .id(1L)
                .productName("Áo thun nam")
                .mainImageUrl("https://mycloudiary/image1.jpg")
                .price(new BigDecimal("200000"))
                .discountPrice(new BigDecimal("150000"))
                .ratingCount(10L)
                .ratingAvg(4.5)
                .build();

        ProductSummaryResponse product2 = ProductSummaryResponse.builder()
                .id(2L)
                .productName("Quần jean nữ")
                .mainImageUrl("https://mycloudiary/image2.jpg")
                .price(new BigDecimal("300000"))
                .discountPrice(new BigDecimal("250000"))
                .ratingCount(5L)
                .ratingAvg(4.0)
                .build();

        when(productRepository.getProductOnSaleList()).thenReturn(List.of(product1, product2));

        // ACT
        List<ProductSummaryResponse> result = productService.getProductOnSaleList();

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
        assertThat(result.getFirst().getProductName()).isEqualTo("Áo thun nam");
        assertThat(result.getFirst().getDiscountPrice()).isEqualTo(new BigDecimal("150000"));

        verify(productRepository, times(1)).getProductOnSaleList();
    }

    @Test
    void getProductOnSaleList_emptyList() {
        // ARRANGE
        when(productRepository.getProductOnSaleList()).thenReturn(List.of());

        // ACT
        List<ProductSummaryResponse> result = productService.getProductOnSaleList();

        // ASSERT
        assertThat(result).isEmpty();
        verify(productRepository, times(1)).getProductOnSaleList();
    }
}
