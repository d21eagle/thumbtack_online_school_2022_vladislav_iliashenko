package net.thumbtack.school.hiring.dto.response;
import java.util.UUID;

public class RegisterEmployerDtoResponse {

    private UUID token;

    public RegisterEmployerDtoResponse(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
