package net.thumbtack.school.hiring.model;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    private List<Skill> skills = new ArrayList<>();

    // REVU похоже, не нужен, не используется
    // проверьте
    public Employee(int id,
                    String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password,
                    List<Skill> skills) {
        super(id, email, lastname, firstName, middleName, login, password);
        setSkills(skills);
    }
}
