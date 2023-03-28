package com.yaloostore.shop.auth.advice;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.auth.exeption.InvalidAuthorizationHeaderException;
import com.yaloostore.shop.auth.exeption.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 인증, 인가 관련 에러 처리를 공통으로 진행하는 controller advice 입니다.
 * */
public class AuthControllerAdvice {

    @ExceptionHandler({InvalidAuthorizationHeaderException.class})
    public ResponseEntity<ResponseDto<Object>> invalidAuthExceptionHandler(Exception e){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.builder()
                        .success(false)
                        .status(HttpStatus.UNAUTHORIZED)
                        .errorMessages(List.of(e.getMessage()))
                        .build());

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseDto<Object>> invalidTokenExceptionHandler(Exception e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDto.builder()
                        .success(false)
                        .status(HttpStatus.FORBIDDEN)
                        .errorMessages(List.of(e.getMessage()))
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> authExceptionHandler(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.builder()
                        .success(false)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessages(List.of(e.getMessage()))
                        .build());
    }
}
