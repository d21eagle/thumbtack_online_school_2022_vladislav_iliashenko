package net.thumbtack.school.hiring.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Skill {
    private int id;
    private Employee employee;
    private String skillName;
    private int profLevel;

    public Skill(String skillName, int profLevel) {
        this.skillName = skillName;
        this.profLevel = profLevel;
    }
}
