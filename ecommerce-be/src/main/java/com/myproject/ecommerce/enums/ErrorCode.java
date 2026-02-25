package com.myproject.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_EXISTED(1001, "username existed!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002, "username invalid!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "password invalid!", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1004, "email invalid!", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1005, "account not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "you have no permission with this page!", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1008, "user not found!", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1009, "product not found!", HttpStatus.NOT_FOUND),
    FAV_PRODUCT_EXISTED(1010, "favourite product existed!", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1011, "order not found!", HttpStatus.NOT_FOUND),
    USER_REVIEWED(1012, "user reviewed this product!", HttpStatus.BAD_REQUEST),
    FILE_NAME_INVALID(1013, "file name is invalid!", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(1014, "upload failed!", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INCORRECT(1015, "old password is incorrect!", HttpStatus.BAD_REQUEST)
    ;


    private final int code; // future use
    private final String message;
    private final HttpStatusCode statusCode;
}
