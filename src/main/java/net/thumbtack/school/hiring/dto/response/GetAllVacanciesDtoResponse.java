package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllVacanciesDtoResponse {
    private List<GetVacancyDtoResponse> vacancies;
}
