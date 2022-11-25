package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;

public class TestEmployerService {
    Server server = new Server();
    final Gson GSON = new Gson();
    final int SUCCESS_CODE = 200;
    final int ERROR_CODE = 400;

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @Test
    public void testRegisterAndLoginEmployer() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), SUCCESS_CODE);

        LoginEmployerDtoRequest loginJson = new LoginEmployerDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginEmployer(GSON.toJson(loginJson));
        LoginEmployerDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginEmployerDtoResponse.class);

        ServerResponse getEmployerJson = server.getEmployerByToken(loginEmployerDtoResponse.getToken());
        GetEmployerByTokenDtoResponse getEmployerResponse = GSON.fromJson(getEmployerJson.getResponseData(), GetEmployerByTokenDtoResponse.class);

        assertEquals(requestJson.getEmail(), getEmployerResponse.getEmployer().getEmail());
        assertEquals(requestJson.getLastName(), getEmployerResponse.getEmployer().getLastName());
        assertEquals(requestJson.getMiddleName(), getEmployerResponse.getEmployer().getMiddleName());
        assertEquals(requestJson.getFirstName(), getEmployerResponse.getEmployer().getFirstName());
        assertEquals(requestJson.getLogin(), getEmployerResponse.getEmployer().getLogin());
        assertEquals(requestJson.getPassword(), getEmployerResponse.getEmployer().getPassword());
    }

    @Test
    public void testLogoutEmployer() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "InventInc",
                "просп.Мира д.81/1",
                "invent_inc.future@gmail.com",
                "Андреев",
                "Андреевич",
                "Андрей",
                "inventInc.hr",
                "87686548440"
        );
        ServerResponse actualResponse1 = server.registerEmployer(GSON.toJson(requestJson));

        LoginEmployerDtoRequest loginJson = new LoginEmployerDtoRequest(
                "inventInc.hr",
                "87686548440"
        );
        ServerResponse tokenJson = server.loginEmployee(GSON.toJson(loginJson));
        LoginEmployerDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginEmployerDtoResponse.class);

        LogoutEmployerDtoRequest logoutJson = new LogoutEmployerDtoRequest(loginEmployerDtoResponse.getToken());
        ServerResponse logoutResponse = server.logoutEmployer(GSON.toJson(logoutJson));

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployerByTokenJson = server.getEmployeeByToken(loginEmployerDtoResponse.getToken());
        assertEquals(getEmployerByTokenJson.getResponseCode(), ERROR_CODE);
    }
}
