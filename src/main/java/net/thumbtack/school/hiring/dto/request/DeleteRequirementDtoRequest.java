package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRequirementDtoRequest {
    private int vacancyId;
    private int requirementId;
}
