package com.yaloostore.shop.member.exception;

public class AlreadyExistsMember extends RuntimeException {

    public AlreadyExistsMember() {
        super("already exist member!!");
    }
}
