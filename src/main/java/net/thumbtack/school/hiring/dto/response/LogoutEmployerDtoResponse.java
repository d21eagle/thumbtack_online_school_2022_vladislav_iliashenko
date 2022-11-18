package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.server.ServerResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutEmployerDtoResponse {
    private ServerResponse response;
}
