package net.thumbtack.school.hiring.dto.response;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginEmployerDtoResponse {
    private UUID token;
}
