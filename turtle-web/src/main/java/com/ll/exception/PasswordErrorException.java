package com.ll.exception;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends RuntimeException {

    public PasswordErrorException() {

    }

    public PasswordErrorException(String message) {
        super(message);
    }
}
