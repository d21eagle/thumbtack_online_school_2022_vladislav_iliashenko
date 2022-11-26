package net.thumbtack.school.hiring.dto.response;
import lombok.*;
import net.thumbtack.school.hiring.server.ServerResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutEmployeeDtoResponse {
    // REVU почему внутри ServerResponse ? Наоборот же, response в виде json вкладывается в ServerResponse
    // а тут просто пустой класс
    private ServerResponse response;
}
