package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
import com.myproject.ecommerce.dto.request.WishListRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.dto.response.UserInfoResponse;
import com.myproject.ecommerce.service.ProductService;
import com.myproject.ecommerce.service.UserService;
import com.myproject.ecommerce.service.WishListService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final WishListService wishListService;

    @PostMapping("")
    public ResponseEntity<UserInfoResponse> createUser(@RequestBody InfoUpdateRequest infoUpdateRequest) {
        return ResponseEntity.ok(userService.createUser(infoUpdateRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateInfo(
            @PathVariable("userId") Long id, @Valid @RequestBody InfoUpdateRequest infoUpdateRequest) {

        var apiResponse = new ApiResponse<>(true, null, userService.updateUserInfo(id, infoUpdateRequest));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/me/wish-list")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getMyWishlist(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        var apiResponse = new ApiResponse<>(true, null, productService.getMyWishlist(accountId));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/me/wish-list")
    public ResponseEntity<ApiResponse<Void>> addProductToMyWishList(
            @AuthenticationPrincipal Jwt jwt, @RequestBody WishListRequest wishListRequest) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        Long productId = wishListRequest.getProductId();
        wishListService.insertToWishList(accountId, productId);

        var apiResponse = new ApiResponse<Void>(true, null, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/me/wish-list/{productId}/exists")
    public ResponseEntity<ApiResponse<?>> getWishListStatus(
            @PathVariable("productId") Long productId, @AuthenticationPrincipal Jwt jwt) {
        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        var apiResponse = new ApiResponse<>(true, null, productService.isWishListed(productId, accountId));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/me/wish-list/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteProductFromMyWishList(
            @PathVariable("productId") Long productId, @AuthenticationPrincipal Jwt jwt) {
        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        wishListService.delete(accountId, productId);
        var apiResponse = new ApiResponse<>(true, null, null);
        return ResponseEntity.noContent().build();
    }
}
