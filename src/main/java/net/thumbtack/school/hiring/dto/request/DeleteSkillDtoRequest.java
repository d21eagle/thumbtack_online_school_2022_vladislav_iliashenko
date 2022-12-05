package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteSkillDtoRequest {
    private int userId;
    private int skillId;
}
