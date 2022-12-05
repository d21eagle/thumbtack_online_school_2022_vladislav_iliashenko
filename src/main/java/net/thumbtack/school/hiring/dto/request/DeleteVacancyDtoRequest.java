package net.thumbtack.school.hiring.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVacancyDtoRequest {
    private int userId;
    private int vacancyId;
}
