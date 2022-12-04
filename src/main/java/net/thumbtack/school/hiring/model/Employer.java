package net.thumbtack.school.hiring.model;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends User {

    private String companyName;
    private String companyAddress;
    private List<Vacancy> vacancies;

    public Employer(int id,
                    String companyName,
                    String companyAddress,
                    String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password,
                    List<Vacancy> vacancies) {
        super(id, email, lastname, firstName, middleName, login, password);
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        setVacancies(vacancies);
    }
}
