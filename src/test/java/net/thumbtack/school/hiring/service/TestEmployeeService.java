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

        EmployeeService employeeService = new EmployeeService();
        ServerResponse actualResponse = employeeService.registerEmployee(GSON.toJson(requestJson));
        EmptyResponse emptyResponse = new EmptyResponse();
        ServerResponse expectedResponse = new ServerResponse(SUCCESS_CODE, GSON.toJson(emptyResponse));
        assertTrue(expectedResponse.equals(actualResponse));
    }

    @Test
    public void TestRegisterEmployee_Failed() {
        final int ERROR_CODE = 400;
        final Gson GSON = new Gson();
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
        ServerResponse actualResponse = employeeService.registerEmployee(GSON.toJson(requestJson));
        ErrorResponse errorResponse = new ErrorResponse(e);
        ServerResponse expectedResponse = new ServerResponse(ERROR_CODE, GSON.toJson(errorResponse));
        assertTrue(expectedResponse.equals(actualResponse));
    }
}
