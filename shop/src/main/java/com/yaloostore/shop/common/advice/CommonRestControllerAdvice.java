package com.yaloostore.shop.common.advice;

import com.yaloostore.shop.member.exception.AlreadyExistsMember;
import com.yaloostore.shop.member.exception.AlreadyExistsMemberProfile;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.exception.NotFoundMemberRoleException;
import com.yaloostore.shop.product.exception.NotFoundProductTypeException;
import com.yaloostore.shop.role.exception.NotFoundRoleTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonRestControllerAdvice {

    /**
     * NOT_FOUND와 관련된 예외를 처리하는 핸들러
     * */
    @ExceptionHandler(value = {NotFoundMemberException.class,
            NotFoundMemberRoleException.class,
            NotFoundRoleTypeException.class,
            NotFoundProductTypeException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> notFoundExceptionHandler(Exception ex){
        log.error("[NOT_FOUND] notFoundExceptionHandler ", ex);
        return ResponseEntity.notFound().build();
    }


    /**
     * CONFLICT와 관련된 예외를 처리하는 핸들러
     * */
    @ExceptionHandler(value = {AlreadyExistsMember.class,
            AlreadyExistsMemberProfile.class

    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> alreadyExistsExceptionHandler(Exception ex){
        log.error("[CONFLICT] alreadyExistsExceptionHandler ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}
