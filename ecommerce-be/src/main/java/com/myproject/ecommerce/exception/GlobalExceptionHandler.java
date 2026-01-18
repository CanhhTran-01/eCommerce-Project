package com.myproject.ecommerce.exception;

import org.hibernate.TransientObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlingRuntimeException(RuntimeException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlingValidation(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(TransientObjectException.class)
    public ResponseEntity<String> handlingRelationalErrorInDB(TransientObjectException ex){
        // phía service không cần throw vì exception này Hibernate tự throw dồi
        return ResponseEntity.badRequest().body(
                ex.getMessage() + " - bên owning side phải có trước (hoặc dùng cascade)");
    }

}
