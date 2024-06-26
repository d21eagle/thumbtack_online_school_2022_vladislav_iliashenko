package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeDtoResponse {
    private int userId;
    private String email;
    private String login;
    private String lastName;
    private String middleName;
    private String firstName;
}
