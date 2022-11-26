package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Employer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployerByTokenDtoResponse {
    // REVU нет, нелья в response помещать класс модели
    // придется переписать поля, а для заполнения - lombok
    private Employer employer;
}
