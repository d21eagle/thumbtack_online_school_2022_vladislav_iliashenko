package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Employee;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeByTokenDtoResponse {
    // REVU нет, нелья в response помещать класс модели
    // придется переписать поля, а для заполнения - lombok
    private Employee employee;
}
