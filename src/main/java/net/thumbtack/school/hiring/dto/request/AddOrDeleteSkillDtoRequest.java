package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrDeleteSkillDtoRequest {
    private String skillName;
    private int profLevel;
}
