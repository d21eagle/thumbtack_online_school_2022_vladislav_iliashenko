package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeByTokenDtoResponse {
    private String email;
    private String login;
    private String lastName;
    private String middleName;
    private String firstName;
    private List<SkillInnerDtoResponse> skills;
}
