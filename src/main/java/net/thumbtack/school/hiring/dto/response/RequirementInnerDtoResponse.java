package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementInnerDtoResponse {
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
