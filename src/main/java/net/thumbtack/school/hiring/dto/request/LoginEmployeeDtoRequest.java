package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@AllArgsConstructor
public class LoginEmployeeDtoRequest {
    private String login;
    private String password;
}
