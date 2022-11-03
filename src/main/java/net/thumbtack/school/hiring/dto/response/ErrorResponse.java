package net.thumbtack.school.hiring.dto.response;
import net.thumbtack.school.hiring.exception.*;

public class ErrorResponse {

    private String errorResp;

    public ErrorResponse(ServerException exception) {
        this.errorResp = exception.getErrorCode().getErrorString();
    }
}
