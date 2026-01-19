package com.myproject.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_EXISTED (1002, "username existed!"),
    USERNAME_INVALID(1003, "username invalid!"),
    PASSWORD_INVALID(1004, "password invalid!"),
    ACCOUNT_NOT_FOUND(1005, "account not found!"),
    EMAIL_INVALID(1006, "email invalid!")
    ;


    private final int code;
    private final String message;
}
