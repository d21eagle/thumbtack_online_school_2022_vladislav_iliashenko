package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class LoginEmployerDtoRequest {
    private String login;
    private String password;
}
