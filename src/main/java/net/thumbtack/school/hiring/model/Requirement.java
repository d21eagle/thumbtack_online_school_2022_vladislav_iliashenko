package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Requirement {
    private int requirementId;
    private Vacancy vacancy;
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;

    public Requirement(String requirementName, int profLevel, boolean isNecessary) {
        this.requirementName = requirementName;
        this.profLevel = profLevel;
        this.isNecessary = isNecessary;
    }
}
