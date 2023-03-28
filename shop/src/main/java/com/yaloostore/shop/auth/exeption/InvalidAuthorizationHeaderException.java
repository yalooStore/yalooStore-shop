package com.yaloostore.shop.auth.exeption;

public class InvalidAuthorizationHeaderException extends RuntimeException {

    public InvalidAuthorizationHeaderException() {
        super("invalid authorization header");
    }
}
