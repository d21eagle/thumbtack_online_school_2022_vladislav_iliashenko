package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.model.Employer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployerByTokenDtoResponse {
    private Employer employer;
}
