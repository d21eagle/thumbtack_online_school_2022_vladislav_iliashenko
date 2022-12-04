package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployerDtoRequest {
    //private int id;
    private String companyName;
    private String companyAddress;
    private String email;
    private String lastName;
    private String middleName;
    private String firstName;
    private String login;
    private String password;
}
