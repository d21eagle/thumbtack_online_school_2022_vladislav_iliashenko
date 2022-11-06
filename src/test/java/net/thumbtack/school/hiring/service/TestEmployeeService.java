package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.response.EmptyResponse;
import net.thumbtack.school.hiring.dto.response.ErrorResponse;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;

public class TestEmployeeService {

    @Test
    public void TestRegisterEmployee_Success() {
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

        // REVU нет, не имеете тут права доступа к сервису. Только к серверу. Его метод и вызывайте
        EmployeeService employeeService = new EmployeeService();
        ServerResponse actualResponse = employeeService.registerEmployee(GSON.toJson(requestJson));
        // REVU не надо создавать EmptyResponse и проверять
        // достаточно проверить код в ServerResponse
        // Если он 200 - ввызов метода был успешен
        EmptyResponse emptyResponse = new EmptyResponse();
        ServerResponse expectedResponse = new ServerResponse(SUCCESS_CODE, GSON.toJson(emptyResponse));
        assertTrue(expectedResponse.equals(actualResponse));
    }

    @Test
    public void TestRegisterEmployee_Failed() {
        final int ERROR_CODE = 400;
        final Gson GSON = new Gson();
        // REVU у Вас тут нет права доступа к внутренностям сервера
        // а именно
        // к сервисам
        // к модели
        // к исключениям
        // к БД
        // да и не нужно это
        ServerException e = new ServerException(ServerErrorCode.SHORT_PASSWORD);

        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иван",
                "Иванович",
                "rocket_ivan",
                "75437"
        );

        EmployeeService employeeService = new EmployeeService();
        // REVU нет, не имеете тут права доступа к сервису. Только к серверу. Его метод и вызывайте
        ServerResponse actualResponse = employeeService.registerEmployee(GSON.toJson(requestJson));
        // REVU а тут надо проверить код из actualResponse, он должен быть 400
        // а потом получить ErrorResponse из  actualResponse.data и проверить текст
        // а кстати, почему этот тест должен упасть ?
        // потому что уже такое было ?
        // тесты независимы, порядок их запуска неопределен
        // и после (или перед) запуском теста надо чистить БД
        // @BeforeEach или @AfterEach
        ErrorResponse errorResponse = new ErrorResponse(e);
        ServerResponse expectedResponse = new ServerResponse(ERROR_CODE, GSON.toJson(errorResponse));
        assertTrue(expectedResponse.equals(actualResponse));
    }
}
