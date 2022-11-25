package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Employee;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeByTokenDtoResponse {
    private Employee employee;
}
