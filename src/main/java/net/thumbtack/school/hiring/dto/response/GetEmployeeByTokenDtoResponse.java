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
    // REVU НИКОГДА не возвращают пароль. НИКОГДА!!!
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
    // REVU нет, нельзя сюда Skill
    // DTO не должен знать модель
    // private List<SkillInnerDtoResponse> skills;

    private List<Skill> skills;
}
