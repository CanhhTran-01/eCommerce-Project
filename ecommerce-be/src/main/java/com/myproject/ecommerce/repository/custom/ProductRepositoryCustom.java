package com.myproject.ecommerce.repository.custom;

import com.myproject.ecommerce.dto.request.ProductFilterSearchRequest;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductSummaryResponse> searchbyFilter(ProductFilterSearchRequest request);
}
