package net.thumbtack.school.hiring.exception;

// REVU хватит и одного исключения на все про все
// ServerException
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
