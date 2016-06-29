package com.mcmanus.fs.model.exceptions;

public class InputException extends Exception {

    private String errorCode;

    public InputException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
