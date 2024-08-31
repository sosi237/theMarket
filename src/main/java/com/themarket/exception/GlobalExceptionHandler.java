package com.themarket.exception;

import com.themarket.dto.ResponseDTO;
import com.themarket.enums.BaseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDTO.builder()
                                .code(BaseEnum.Fail.getCode())
                                .message(ex.getMessage())
                                .build()
                );
    }

    // ValidationException 처리
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseDTO> handleInvalidInputException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDTO.builder()
                                .code(BaseEnum.Fail.getCode())
                                .message(ex.getMessage())
                                .build()
                );
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDTO.builder()
                                .code(BaseEnum.Fail.getCode())
                                .message(ex.getMessage())
                                .build()
                );
    }
}
