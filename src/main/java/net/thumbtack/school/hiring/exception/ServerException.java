package net.thumbtack.school.hiring.exception;

public class ServerException extends Exception {

    private final ServerErrorCode serverErrorCode;
    private String string;
    public ServerException(ServerErrorCode serverErrorCode, String string) {
        this.serverErrorCode = serverErrorCode;
        this.string = string;
    }

    public ServerErrorCode getUserErrorCode() {
        return serverErrorCode;
    }
    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }
}
