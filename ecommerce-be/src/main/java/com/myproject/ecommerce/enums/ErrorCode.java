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
    UNAUTHORIZED(1007, "you have no permission with this page!", HttpStatus.FORBIDDEN)
    ;


    private final int code; // future use
    private final String message;
    private final HttpStatusCode statusCode;
}
