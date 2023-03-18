package com.yaloostore.shop.common.aspect.advisor;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.common.aspect.annotation.LoginId;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * pointcut + advice = advisor
 * aop를 사용해서 parameter로 넘어오는 loginid를 사용해서 해당 유ㅈ
 * */
@Component
@Aspect
public class LoginAdvisor {


    //@Pointcut("execution(* *(.., @com.yaloostore.shop.common.aspect.annotation.LoginId (*), ..)))")
    //@Pointcut("@args(com.yaloostore.shop.common.aspect.annotation.LoginId)")
    @Pointcut("@annotation(com.yaloostore.shop.common.aspect.annotation.LoginId)")
    public void loginIdPointcut(){

    }


    @Around("loginIdPointcut()")
    public Object executeLoginIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        //getArgs()를 하게 되면 넘어오는 parameter를 모두 가져온다.
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();

        //인증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for (int i = 0; i < args.length; i++) {
            if(parameters[i].isAnnotationPresent(LoginId.class)){
                args[i] = getLoginId(parameters[i], authentication);
                break;
            }
        }

        //비지니스 메소드 실행후의 결과 값이 들어있다.
        //이 로직의 경우 Login id의 name이 들어 있다.
        return joinPoint.proceed(args);
    }

    private String getLoginId(Parameter parameter, Authentication authentication) {

        boolean isAnonymous = authentication instanceof AnonymousAuthenticationToken;

        if(parameter.getAnnotation(LoginId.class).required() && isAnonymous){
            throw new ClientException(ErrorCode.UNAUTHORIZED, "unauthorized request");
        }
        if(isAnonymous){
            return null;
        }
        return authentication.getName();

    }




}
