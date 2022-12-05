package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeRequirementDtoResponse {
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
