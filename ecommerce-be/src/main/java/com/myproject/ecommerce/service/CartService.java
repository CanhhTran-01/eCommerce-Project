package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.mapper.CartMapper;
import com.myproject.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;


    // get cart items by account id (with current price and name from product)
    @Transactional(readOnly = true)
    public List<CartItemResponse> getCartItems(Long accountId){
        return cartRepository.findCartItemsDtoByAccountId(accountId);
    }

}
