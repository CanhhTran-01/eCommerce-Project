package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.dto.response.UserInfoResponse;
import com.myproject.ecommerce.service.ProductService;
import com.myproject.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<UserInfoResponse> createUser(@RequestBody InfoUpdateRequest infoUpdateRequest){
        return ResponseEntity.ok(userService.createUser(infoUpdateRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateInfo(@PathVariable("userId") Long id,
                                                                    @RequestBody InfoUpdateRequest infoUpdateRequest){

        var apiResponse = new ApiResponse<>(
                true,
                null,
                userService.updateUserInfo(id, infoUpdateRequest)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/me/wish-list")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getMyWishlist(@AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT

        var apiResponse = new ApiResponse<>(
                true,
                null,
                productService.getMyWishlist(accountId)
        );
        return ResponseEntity.ok(apiResponse);
    }

}
