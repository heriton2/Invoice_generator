package com.challenge.invoice_generator.exception;

public class ImportException extends Exception {
    private String errorCode;

    public ImportException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

