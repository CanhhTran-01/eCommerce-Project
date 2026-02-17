package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;


}
