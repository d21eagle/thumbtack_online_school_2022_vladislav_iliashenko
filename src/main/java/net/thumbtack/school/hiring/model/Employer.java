package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends User {

    private String companyName;
    private String companyAddress;
    // REVU все хорошо, но не добавить ли List/Set<Vacancy> ?

    public Employer(String companyName,
                    String companyAddress,
                    String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password) {
        super(email, lastname, firstName, middleName, login, password);
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
    }
}
