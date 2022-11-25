package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@AllArgsConstructor
public class LoginEmployerDtoRequest {
    private String login;
    private String password;
}
