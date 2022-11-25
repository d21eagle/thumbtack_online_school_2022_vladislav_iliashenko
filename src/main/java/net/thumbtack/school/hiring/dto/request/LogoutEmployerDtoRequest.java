package net.thumbtack.school.hiring.dto.request;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LogoutEmployerDtoRequest {
    private UUID token;
}
