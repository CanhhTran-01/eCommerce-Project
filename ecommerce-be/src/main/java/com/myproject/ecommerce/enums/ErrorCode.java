package com.myproject.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_EXISTED (1001, "username existed!"),
    USERNAME_INVALID(1002, "username invalid!"),
    PASSWORD_INVALID(1003, "password invalid!"),
    EMAIL_INVALID(1004, "email invalid!"),
    ACCOUNT_NOT_FOUND(1005, "account not found!"),
    UNAUTHENTICATED(1006, "unauthenticated!")
    ;


    private final int code;
    private final String message;
}
