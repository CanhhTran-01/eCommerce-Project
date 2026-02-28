package com.myproject.ecommerce.exception;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.enums.ErrorCode;
import java.util.Objects;
import org.hibernate.TransientObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception) {

        ApiResponse<?> apiResponse = new ApiResponse<>(false, null, exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handlingBaseException(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<?> apiResponse =
                new ApiResponse<>(false, exception.getErrorCode().getMessage(), null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {

        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ApiResponse<?> apiResponse = new ApiResponse<>(false, errorCode.getMessage(), null);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(TransientObjectException.class)
    public ResponseEntity<String> handlingRelationalErrorInDB(TransientObjectException ex) {
        // Hibernate auto throw this exception insted of throw it manually in service
        return ResponseEntity.badRequest()
                .body(ex.getMessage() + " - bên owning side phải có trước (hoặc dùng cascade)");
    }
}
