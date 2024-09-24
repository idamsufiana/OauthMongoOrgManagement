package com.ida.management.exception;

public class genException extends Exception {
    private Integer errorCode;
    private String message;
    private String[] values;

    public genException(String message) {
        super(message);
        this.errorCode = 500;
        this.message = message;
    }

    public genException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public genException(Integer errorCode, String message, String... values) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.values = values;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getValues() {
        return this.values;
    }
}
