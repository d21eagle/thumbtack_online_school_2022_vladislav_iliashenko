package net.thumbtack.school.hiring.model;
import lombok.*;

import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    private String position;
    private int salary;
    private List<EmployeeRequirement> requirementsList;
    // REVU а чья вакансия ? Непонятно
    // можно добавить Employer employer
}
