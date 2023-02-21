package net.thumbtack.school.hiring.dto.request;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementListDtoRequest {
    private List<RequirementDtoRequest> requirementList;
}
