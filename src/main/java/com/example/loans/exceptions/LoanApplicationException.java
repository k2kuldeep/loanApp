package com.example.loans.exceptions;

public class LoanApplicationException extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    public LoanApplicationException(String message){
        super(message);
        this.errorMessage=message;
    }

    public LoanApplicationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
