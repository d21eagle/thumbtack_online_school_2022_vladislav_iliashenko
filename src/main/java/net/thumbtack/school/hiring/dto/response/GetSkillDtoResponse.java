package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSkillDtoResponse {
    private int skillId;
    private String skillName;
    private int profLevel;
}
