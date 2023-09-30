package com.webshop.demo.customExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class FileUploadExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        String errorMessage = "File size limit exceeded. Please upload a smaller file.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

