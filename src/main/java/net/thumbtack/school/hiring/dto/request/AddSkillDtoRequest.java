package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrDeleteSkillDtoRequest {
    private int id;
    private String skillName;
    private int profLevel;
}
