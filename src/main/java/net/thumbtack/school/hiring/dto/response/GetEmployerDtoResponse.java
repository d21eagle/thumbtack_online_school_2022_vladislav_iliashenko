package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployerDtoResponse {
    private int userId;
    private String email;
    private String login;
    private String lastName;
    private String middleName;
    private String firstName;
    private String companyName;
    private String companyAddress;
}
