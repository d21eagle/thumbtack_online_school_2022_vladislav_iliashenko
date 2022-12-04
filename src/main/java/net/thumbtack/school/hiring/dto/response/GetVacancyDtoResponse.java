package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Employer;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVacancyDtoResponse {
    private Employer employer;
    private String position;
    private int salary;
    private List<EmployeeRequirementInnerDtoResponse> requirementsList;
}
