package net.thumbtack.school.hiring.dto.request;
import lombok.*;
import net.thumbtack.school.hiring.model.Employer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVacancyDtoRequest {
    private String position;
    private int salary;
}
