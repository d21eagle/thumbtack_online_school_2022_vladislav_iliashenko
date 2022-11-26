package net.thumbtack.school.hiring.dto.response;
import lombok.*;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeDtoResponse {

    // REVU никакого токена тут не нужно, да и сам класс не нужен
    private UUID token;

}
