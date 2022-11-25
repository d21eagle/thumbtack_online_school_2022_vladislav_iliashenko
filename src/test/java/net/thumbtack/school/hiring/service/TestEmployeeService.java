package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;

public class TestEmployeeService {
    Server server = new Server();
    final Gson GSON = new Gson();
    final int SUCCESS_CODE = 200;
    final int ERROR_CODE = 400;

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @Test
    public void testRegisterAndLoginEmployee() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), SUCCESS_CODE);

        LoginEmployeeDtoRequest loginJson = new LoginEmployeeDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse tokenJson = server.loginEmployee(GSON.toJson(loginJson));
        LoginEmployeeDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginEmployeeDtoResponse.class);

        ServerResponse getEmployeeByTokenJson = server.getEmployeeByToken(loginEmployeeDtoResponse.getToken());
        GetEmployeeByTokenDtoResponse getEmployeeResponse = GSON.fromJson(getEmployeeByTokenJson.getResponseData(), GetEmployeeByTokenDtoResponse.class);

        assertEquals(requestJson.getEmail(), getEmployeeResponse.getEmployee().getEmail());
        assertEquals(requestJson.getLastName(), getEmployeeResponse.getEmployee().getLastName());
        assertEquals(requestJson.getMiddleName(), getEmployeeResponse.getEmployee().getMiddleName());
        assertEquals(requestJson.getFirstName(), getEmployeeResponse.getEmployee().getFirstName());
        assertEquals(requestJson.getLogin(), getEmployeeResponse.getEmployee().getLogin());
        assertEquals(requestJson.getPassword(), getEmployeeResponse.getEmployee().getPassword());
    }

    @Test
    public void testLogoutEmployee() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "petr.petrov@mail.ru",
                "Петров",
                "Петрович",
                "Пётр",
                "petr_one",
                "982360301"
        );
        ServerResponse actualResponse1 = server.registerEmployee(GSON.toJson(requestJson));

        LoginEmployeeDtoRequest loginJson = new LoginEmployeeDtoRequest(
                "petr_one",
                "982360301"
        );
        ServerResponse tokenJson = server.loginEmployee(GSON.toJson(loginJson));
        LoginEmployeeDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginEmployeeDtoResponse.class);

        LogoutEmployeeDtoRequest logoutJson = new LogoutEmployeeDtoRequest(loginEmployeeDtoResponse.getToken());
        ServerResponse logoutResponse = server.logoutEmployee(GSON.toJson(logoutJson));

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployeeByTokenJson = server.getEmployeeByToken(loginEmployeeDtoResponse.getToken());
        assertEquals(getEmployeeByTokenJson.getResponseCode(), ERROR_CODE);
    }
}
