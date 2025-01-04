package com.ssafy.ssadang.global.error.exception;

public class NullRefreshTokenException extends RuntimeException {
    public NullRefreshTokenException() {
        super("Refresh Token이 null입니다.");
    }
}