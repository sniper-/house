package com.house.common.exception.user;

public class UserPasswordWrongTimesException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserPasswordWrongTimesException(Object[] args)
    {
        super("user.password.retry.limit.times", args);
    }
}
