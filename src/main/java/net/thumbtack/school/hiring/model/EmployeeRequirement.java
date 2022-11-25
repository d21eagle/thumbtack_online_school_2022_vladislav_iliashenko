package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeRequirement {

    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
