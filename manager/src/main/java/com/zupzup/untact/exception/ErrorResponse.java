package com.zupzup.untact.exception;

import lombok.Builder;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ErrorResponse {

    private final int status;
    private final String message;
    private final Integer size;
    private final ArrayList<?> items;

    @Builder
    public ErrorResponse(int status, String message, Integer size, ArrayList<?> items) {

        this.status = status;
        this.message = message;
        this.size = size;
        this.items = items;
    }
}
