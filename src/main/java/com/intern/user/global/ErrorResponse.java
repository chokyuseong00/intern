package com.intern.user.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final Error error;

    public record Error(String code, String message) {}

}
