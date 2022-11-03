package net.thumbtack.school.hiring.dto.response;
import java.util.UUID;

public class RegisterEmployeeDtoResponse {

    private UUID token;

    public RegisterEmployeeDtoResponse(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
