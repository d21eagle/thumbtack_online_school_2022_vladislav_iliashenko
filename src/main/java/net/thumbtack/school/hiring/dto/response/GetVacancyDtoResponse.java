package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVacancyDtoResponse {
    private int vacancyId;
    private String position;
    private int salary;
}
