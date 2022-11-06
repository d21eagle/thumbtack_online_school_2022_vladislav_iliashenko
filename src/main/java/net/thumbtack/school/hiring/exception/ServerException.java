package net.thumbtack.school.hiring.exception;

public class ServerException extends Exception {

    private ServerErrorCode errorCode;

    public ServerException(ServerErrorCode errorCode) {
        // REVU лишнее. И так все есть
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public ServerErrorCode getErrorCode() {
        return errorCode;
    }
}
