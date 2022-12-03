package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDtoRequest {
    private String skillName;
    private int profLevel;
}
