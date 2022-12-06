package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private int skillId;
    private Employee employee;
    private String skillName;
    private int profLevel;
}
