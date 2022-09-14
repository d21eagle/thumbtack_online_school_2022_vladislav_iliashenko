package net.thumbtack.school.colors;

public class ColorException extends Exception {

    private ColorErrorCode errorCode;

    public ColorException(String message) {
        super(message);
    }

    public ColorException(ColorErrorCode errorCode, String cause) {
        super(String.format(errorCode.getErrorString(), cause));
        this.errorCode = errorCode;

    }

    public ColorException(ColorErrorCode errorCode) {
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public ColorException(ColorErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorString(), cause);
        this.errorCode = errorCode;
    }

    public ColorErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ColorErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
