package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSetDtoResponse {
    private Set<EmployeeDtoResponse> employeeDtoResponseSet;
}
