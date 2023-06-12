package net.thumbtack.school.hiring.model;
import lombok.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private int vacancyId;
    private Employer employer;
    private String position;
    private int salary;
    private List<Requirement> requirementsList = new ArrayList<>();

    public Vacancy(int vacancyId) {
        this.vacancyId = vacancyId;
    }

    public void add(Requirement requirement) {
        getRequirementsList().add(requirement);
    }

    public void delete(Requirement requirement) {
        getRequirementsList().remove(requirement);
    }

    public void deleteAll() {
        getRequirementsList().clear();
    }
}
