package net.thumbtack.school.hiring.dto.request;

public class RegisterEmployeeDtoRequest extends RegisterUserDtoRequest {

    public RegisterEmployeeDtoRequest(String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password) {
        super(email, lastname, firstName, middleName, login, password);
    }
}
