package com.runblog.aterweather.exception;

public class AterException extends RuntimeException{
    private String errorCode;
    private Object errorObject;

    public AterException(String errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public AterException(String errorCode,String message,Object errorObject){
        super(message);
        this.errorCode=errorCode;
        this.errorObject = errorObject;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
