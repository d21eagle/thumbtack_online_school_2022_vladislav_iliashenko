package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployeeRequirementDtoRequest {
    // REVU это чей id ? Вакансии ? Тогда vacancyId
    // а сейчас это выглядит как передача id самого Requirement, что понять невозможно - id ему ставит БД, а не в запросе передается
    private int id;
    private String requirementName;
    private int profLevel;
    private boolean isNecessary;
}
