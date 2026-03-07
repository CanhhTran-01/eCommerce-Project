package com.myproject.ecommerce.exception;

import com.myproject.ecommerce.dto.response.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // handle general exception ( error first appear )
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handlingRuntimeException(Exception exception) {

        log.error("uncategorized exception: ", exception);

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        ApiResponse<?> apiResponse = new ApiResponse<>(false, errorCode.getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // handle security exception with method layer
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handlingSecurityException(AccessDeniedException exception) {

        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

        ApiResponse<?> apiResponse = new ApiResponse<>(false, errorCode.getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // handle business exception
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handlingBaseException(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<?> apiResponse =
                new ApiResponse<>(false, exception.getErrorCode().getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // handle exception for validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {

        // get invalid field, ex: username -> get message from that field, ex: USERNAME_INVALID
        var fieldError = exception.getBindingResult().getFieldError();
        String enumKey = Objects.requireNonNull(fieldError).getDefaultMessage();

        // USERNAME_INVALID -> mapping to errorCode.USERNAME_INVALID (third constant in ErrorCode)
        ErrorCode errorCode;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {
            // handle the case of message exists but does not match the enum constant
            errorCode = ErrorCode.NO_MESSAGE_IN_VALIDATION;
        }

        ApiResponse<?> apiResponse = new ApiResponse<>(false, errorCode.getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // other exception ???
    @ExceptionHandler(TransientObjectException.class)
    public ResponseEntity<?> handlingRelationalErrorInDB(TransientObjectException exception) {

        // Hibernate auto throw this exception insted of throw it manually in service
        ApiResponse<?> response = new ApiResponse<>(false, exception.getMessage(), null);

        return ResponseEntity.badRequest().body(response);
    }
}
