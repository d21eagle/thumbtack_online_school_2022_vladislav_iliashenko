package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRequirementsDtoResponse {
    private List<GetRequirementDtoResponse> requirementsList;
}
