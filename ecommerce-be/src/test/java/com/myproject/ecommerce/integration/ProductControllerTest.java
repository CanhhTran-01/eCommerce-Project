package com.myproject.ecommerce.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ecommerce.controller.ProductController;
import com.myproject.ecommerce.service.ProductService;
import com.myproject.ecommerce.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getSaleProductList_returnOk() throws Exception {

        when(productService.getProductOnSaleList()).thenReturn(List.of()); // call method, return empty List

        mockMvc.perform(get("/api/products/sale-list")) // mock reqeust to this endpoint
                .andExpect(status().isOk()) // expect HttpStatusCode = 200 OK
                .andExpect(jsonPath("$.success").value(true)) // expect fied success: true in JSON response
                .andExpect(jsonPath("$.data").isArray()); // expect array data trong JSON response
    }
}
