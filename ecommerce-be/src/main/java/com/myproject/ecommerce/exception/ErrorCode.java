package com.myproject.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_MESSAGE_IN_VALIDATION(
            9998, "you need to insert messsage for validated field in this request ?", HttpStatus.EXPECTATION_FAILED),
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
    OLD_PASSWORD_INCORRECT(1015, "old password is incorrect!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1016, "email existed!", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1017, "OTP expired!", HttpStatus.BAD_REQUEST),
    OTP_INVALID(1018, "OTP invalid!", HttpStatus.BAD_REQUEST),
    OTP_ALREADY_SENT(1019, "hold on for a new OTP (2 minutes)!", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(1020, "username not found !", HttpStatus.BAD_REQUEST),
    OTP_NOT_VERIFIED(1021, "OTP doesn't verify", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTS(1022, "email doesn't exist !", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1023, "category doesn't exist", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGES_EMPTY(1024, "image list is empty!", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND(1025, "cart item doesn't exist!", HttpStatus.BAD_REQUEST),
    DATE_OF_BIRTH_INVALID(1026, "you need to have at least 10 years old!", HttpStatus.BAD_REQUEST),
    NO_RATING(1027, "rating mustn't be empty !", HttpStatus.BAD_REQUEST),
    INVALID_RATING_DATA(1028, "rating is only from 1-5 value!", HttpStatus.BAD_REQUEST),
    NO_TITLE_DATA(1029, "title mustn't be empty !", HttpStatus.BAD_REQUEST),
    NO_COMMENT_DATA(1030, "comment mustn't be empty !", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1031, "phone number is invalid !", HttpStatus.BAD_REQUEST);

    private final int code; // future use
    private final String message;
    private final HttpStatusCode statusCode;
}
