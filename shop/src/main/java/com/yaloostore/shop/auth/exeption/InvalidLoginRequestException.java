package com.yaloostore.shop.auth.exeption;

public class InvalidLoginRequestException extends RuntimeException {

    public InvalidLoginRequestException() {
        super("invalid login request exception");
    }
}
