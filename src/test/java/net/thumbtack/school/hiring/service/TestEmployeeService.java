package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;

import java.util.UUID;

public class TestEmployeeService {
    Server server = new Server();
    final Gson GSON = new Gson();
    final int SUCCESS_CODE = 200;
    final int ERROR_CODE = 400;

    // REVU перед каждым (или после каждого) теста надо чистить БД
    // @BeforeEach
    // и в нем server.clear();
    // а server.clear, как обычно, вызывает сервис и т.д до Database, а в ней метод clear чистит все коллекции
    @Test
    public void testLoginEmployeeSuccess() throws ServerException {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иван",
                "Иванович",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), SUCCESS_CODE);

        LoginEmployeeDtoRequest loginJson = new LoginEmployeeDtoRequest(
                "rocket_ivan",
                "754376579"
        );
        server.loginEmployee(GSON.toJson(loginJson));
        UUID token = server.getToken("rocket_ivan");
        Employee employee = server.getEmployeeByToken(token);

        assertEquals(requestJson.getFirstName(), employee.getFirstName());
        assertEquals(requestJson.getLastName(), employee.getLastName());
        assertEquals(requestJson.getLogin(), employee.getLogin());
        assertEquals(requestJson.getPassword(), employee.getPassword());
    }

    @Test
    public void testRegisterEmployeeFailed() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иван",
                "Иванович",
                "rocket_ivan",
                "75437"
        );

        ServerResponse actualResponse = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(
                actualResponse.getResponseData(),
                GSON.toJson(new ErrorResponse(new ServerException(ServerErrorCode.SHORT_PASSWORD))));
    }
}
