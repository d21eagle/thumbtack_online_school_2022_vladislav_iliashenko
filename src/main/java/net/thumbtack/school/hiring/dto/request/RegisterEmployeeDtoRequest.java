package net.thumbtack.school.hiring.dto.request;
import java.util.*;
import lombok.*;
import net.thumbtack.school.hiring.model.Skill;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeDtoRequest {

    private String email;
    private String lastName;
    private String middleName;
    private String firstName;
    private String login;
    private String password;
}
