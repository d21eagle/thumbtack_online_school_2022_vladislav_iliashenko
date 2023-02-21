package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDtoResponse {
    private String email;
    private String login;
    private String lastName;
    private String middleName;
    private String firstName;
}
