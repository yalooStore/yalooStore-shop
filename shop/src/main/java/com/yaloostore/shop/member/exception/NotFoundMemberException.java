package com.yaloostore.shop.member.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {
        super("not found member!!");
    }
}
