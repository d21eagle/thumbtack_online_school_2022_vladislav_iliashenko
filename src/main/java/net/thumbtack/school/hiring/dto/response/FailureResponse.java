package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.exception.ServerErrorCode;

public class FailureResponse {
    private ServerErrorCode errorCode;
    private String message;
    public FailureResponse(ServerErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }
    public FailureResponse(ServerErrorCode errorCode) {
        this(errorCode, "");
    }
    public ServerErrorCode getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
}
