package com.yaloostore.shop.auth.exeption;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {

        super("Invalid Token exception");
    }
}
