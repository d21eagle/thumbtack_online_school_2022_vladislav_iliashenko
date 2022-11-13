package net.thumbtack.school.hiring.dto.response;
import net.thumbtack.school.hiring.exception.*;

import java.util.Objects;

public class ErrorResponse {

    private String errorResp;

    public ErrorResponse(ServerException exception) {
        this.errorResp = exception.getErrorCode().getErrorString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(errorResp, that.errorResp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorResp);
    }
}
