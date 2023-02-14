package com.yaloostore.shop.member.exception;

public class AlreadyExistsMemberProfile extends RuntimeException {

    public AlreadyExistsMemberProfile() {
        super("already exist member profile");
    }
}
