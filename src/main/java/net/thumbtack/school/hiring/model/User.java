package net.thumbtack.school.hiring.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String email;
    private String login;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
}
