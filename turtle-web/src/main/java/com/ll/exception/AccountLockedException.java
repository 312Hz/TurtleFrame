package com.ll.exception;

/**
 * 账号锁定异常
 */
public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
    }
    public AccountLockedException(String message) {
        super(message);
    }
}
