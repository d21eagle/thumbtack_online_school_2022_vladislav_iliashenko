package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.server.ServerResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutEmployeeDtoResponse {
    private ServerResponse response;
}