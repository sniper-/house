package com.house.common.exception.user;

public class UserPasswordTimesOverException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserPasswordTimesOverException()
    {
        super("user.password.retry.limit.over", null);
    }
}