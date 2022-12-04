package net.thumbtack.school.hiring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private int id;
    private String skillName;
    private int profLevel;
}
