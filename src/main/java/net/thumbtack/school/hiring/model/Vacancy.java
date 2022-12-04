package net.thumbtack.school.hiring.model;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private int id;
    private Employer employer;
    private String position;
    private int salary;
    private List<EmployeeRequirement> requirementsList;
}
