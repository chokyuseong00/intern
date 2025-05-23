package com.intern.user.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 가입된 사용자입니다."),
    UNMATCHED_USER_DATA(HttpStatus.FORBIDDEN, "요청자의 정보가 아닙니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    ADMIN_PIN_NULL(HttpStatus.BAD_REQUEST, "관리자 PIN 번호가 없거나 NULL 입니다."),
    INVALID_ADMIN_PIN(HttpStatus.BAD_REQUEST, "관리자 PIN 번호가 일치하지 않습니다."),
    USERNAME_INVALID(HttpStatus.BAD_REQUEST, "username이 비어있거나 형식에 맞지 않습니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "password가 비어있거나 형식에 맞지 않습니다."),
    NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "nickname이 비어있거나 형식에 맞지 않습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
