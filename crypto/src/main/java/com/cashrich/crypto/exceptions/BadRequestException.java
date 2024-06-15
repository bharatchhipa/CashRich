package com.cashrich.crypto.exceptions;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Object data;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object data) {
        super(message);
        this.data=data;
    }

    public Object getData() {
        return data;
    }
}
