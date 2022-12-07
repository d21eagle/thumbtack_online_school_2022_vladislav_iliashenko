package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Requirement {
    private int requirementId;
    private Vacancy vacancy;
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
