package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployerByTokenDtoResponse {
    private String email;
    private String login;
    private String password;
    private String lastName;
    private String middleName;
    private String firstName;
    private String companyName;
    private String companyAddress;
}
