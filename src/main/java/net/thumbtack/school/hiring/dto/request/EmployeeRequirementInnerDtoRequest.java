package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequirementInnerDtoRequest {
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
