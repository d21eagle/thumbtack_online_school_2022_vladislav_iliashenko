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

    // REVU перед каждым (или после каждого) теста надо чистить БД
    // @BeforeEach
    // и в нем server.clear();
    // а server.clear, как обычно, вызывает сервис и т.д до Database, а в ней метод clear чистит все коллекции
    //
    @Test
    // REVU не используйте _ в именах
    // имя метода должно начинаться со строчной буквы
    // testRegisterEmployeeSuccess или лучше просто testRegisterEmployee
    public void TestRegisterEmployee_Success() {
        // REVU сделайте членом класса. Создавть сервер в каждом тесте незачем
        Server server = new Server();
        // REVU сделайте оба членами класса
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
        // REVU верно, но мало
        // а вдруг имя попало в lastname, а фамилия - в firstname ?
        // надо выполнить логин, получить токен, по токену получить Employee (server.getEmployeeByToken)
        // и сравнить то, что получили с тем, что передали в RegisterEmployeeDtoRequest
    }

    @Test
    // REVU не используйте _ в именах
    // имя метода должно начинаться со строчной буквы
    // testRegisterEmployeeFailed
    public void TestRegisterEmployee_Failed() {
        Server server = new Server();
        final int ERROR_CODE = 400;
        final Gson GSON = new Gson();
        // REVU не нужен
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
        // REVU не нужно. возьмите actualResponse.getResponseData(), конвертируйте в ErrorResponse и проверьте error
        error.addProperty("errorResp", "Пароль слишком короткий!");
        assertEquals(actualResponse.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse.getResponseData(), error.toString());
    }
}
