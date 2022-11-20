package net.thumbtack.school.hiring.model;
import lombok.*;

import java.util.Objects;
// REVU а можно @Data
// https://projectlombok.org/features/Data
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;
    private String login;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
}
