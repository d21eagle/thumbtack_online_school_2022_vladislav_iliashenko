package net.thumbtack.school.hiring.exception;

public class ValidateException extends Exception {

    private ValidateErrorCode errorCode;

    public ValidateException(ValidateErrorCode errorCode) {
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public ValidateErrorCode getErrorCode() {
        return errorCode;
    }
}
