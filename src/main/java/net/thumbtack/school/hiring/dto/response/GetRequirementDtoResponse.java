package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRequirementDtoResponse {
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
