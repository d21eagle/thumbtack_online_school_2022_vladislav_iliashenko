package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVacancyDtoResponse {
    private int vacancyId;
    private String position;
    private int salary;
}
