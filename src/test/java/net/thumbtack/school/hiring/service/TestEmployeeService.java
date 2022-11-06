package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TestEmployeeService {

    @BeforeEach
    @Test
    public void TestRegisterEmployee_Success() {
        Server server = new Server();
        final int SUCCESS_CODE = 200;
        final Gson GSON = new Gson();

        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иван",
                "Иванович",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse.getResponseCode(), SUCCESS_CODE);
    }

    @BeforeEach
    @Test
    public void TestRegisterEmployee_Failed() {
        Server server = new Server();
        final int ERROR_CODE = 400;
        final Gson GSON = new Gson();
        JsonObject error = new JsonObject();

        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иван",
                "Иванович",
                "rocket_ivan",
                "75437"
        );

        ServerResponse actualResponse = server.registerEmployee(GSON.toJson(requestJson));
        error.addProperty("errorResp", "Пароль слишком короткий!");
        assertEquals(actualResponse.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse.getResponseData(), error.toString());
    }
}
