package net.thumbtack.school.hiring.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends User {

    private String companyName;
    private String companyAddress;
    private List<Vacancy> vacancies = new ArrayList<>();

    public void add(Vacancy vacancy) {
        getVacancies().add(vacancy);
    }

    public void delete(Vacancy vacancy) {
        getVacancies().remove(vacancy);
    }
}
