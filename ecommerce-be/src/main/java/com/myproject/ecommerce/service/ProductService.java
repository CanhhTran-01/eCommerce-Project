package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductOnSaleResponse;
import com.myproject.ecommerce.entity.Product;
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

    public ProductOnSaleResponse createProduct(ProductRequest productRequest){
        Product product = productMapper.toEntity(productRequest);

        // set product code
        product.setProductCode(ProductCodeMakingUtils.generateProductCode());

        return productMapper.toProductOnSaleResponse(productRepository.save(product));
    }

    // get product on sale list
    @Transactional(readOnly = true)
    public List<ProductOnSaleResponse> getProductOnSaleList(){
        return productRepository.getProductOnSaleList()
                .stream()
                .map(productMapper::toProductOnSaleResponse)
                .toList();
    }

    public ProductOnSaleResponse updateProject(Long id, ProductRequest productRequest){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        productMapper.update(product, productRequest);
        return productMapper.toProductOnSaleResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
