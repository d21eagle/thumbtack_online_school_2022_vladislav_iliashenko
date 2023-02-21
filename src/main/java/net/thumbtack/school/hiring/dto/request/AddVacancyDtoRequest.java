package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVacancyDtoRequest {
    private String position;
    private int salary;
}
