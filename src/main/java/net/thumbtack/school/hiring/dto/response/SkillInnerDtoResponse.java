package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillInnerDtoResponse {
    private int id;
    private String skillName;
    private int profLevel;
}
