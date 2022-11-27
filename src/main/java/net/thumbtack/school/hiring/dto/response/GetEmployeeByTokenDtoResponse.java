package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Skill;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeByTokenDtoResponse {
    private String email;
    private String login;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
    private List<Skill> skills;
}
