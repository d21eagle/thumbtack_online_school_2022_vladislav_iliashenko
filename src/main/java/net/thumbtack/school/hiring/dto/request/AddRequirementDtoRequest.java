package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRequirementDtoRequest {
    private int vacancyId;
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
