package com.yaloostore.shop.member.exception;

public class NotFoundMemberRoleException extends RuntimeException {

    public NotFoundMemberRoleException() {
        super("member role not found");
    }
}
