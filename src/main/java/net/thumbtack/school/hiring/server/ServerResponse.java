package net.thumbtack.school.hiring.server;
import net.thumbtack.school.hiring.exception.ServerException;
import lombok.*;

@Data
@AllArgsConstructor
public class ServerResponse {

    private int responseCode;
    private String responseData;
    private static final int ERROR_CODE = 400;

    public ServerResponse(ServerException exception) {
        setResponseCode(ERROR_CODE);
        setResponseData(exception.getErrorCode().getErrorString());
    }
}
