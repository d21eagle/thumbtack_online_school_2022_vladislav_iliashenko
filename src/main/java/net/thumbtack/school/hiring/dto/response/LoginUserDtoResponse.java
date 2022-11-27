package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDtoResponse {
    private UUID token;
}
