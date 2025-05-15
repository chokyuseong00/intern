package com.intern.user.global.exception;

import com.intern.user.global.ErrorResponse;
import com.intern.user.global.ErrorResponse.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String message = exception.getMessage();

        ErrorResponse.Error error = new Error(errorCode.name(), message);
        ErrorResponse body = new ErrorResponse(error);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }
}
