package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;

public class TestEmployerService extends TestBase {
    // REVU см. REVU в TestEmployeeService
    final Gson GSON = new Gson();
    final int SUCCESS_CODE = 200;
    final int ERROR_CODE = 400;

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

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        ServerResponse getEmployerJson = server.getEmployerByToken(loginEmployerDtoResponse.getToken());
        GetEmployerByTokenDtoResponse getEmployerResponse = GSON.fromJson(getEmployerJson.getResponseData(), GetEmployerByTokenDtoResponse.class);

        assertEquals(requestJson.getEmail(), getEmployerResponse.getEmail());
        assertEquals(requestJson.getLastName(), getEmployerResponse.getLastName());
        assertEquals(requestJson.getMiddleName(), getEmployerResponse.getMiddleName());
        assertEquals(requestJson.getFirstName(), getEmployerResponse.getFirstName());
        assertEquals(requestJson.getLogin(), getEmployerResponse.getLogin());
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

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "inventInc.hr",
                "87686548440"
        );
        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        LogoutUserDtoRequest logoutJson = new LogoutUserDtoRequest(loginEmployerDtoResponse.getToken());
        ServerResponse logoutResponse = server.logoutUser(GSON.toJson(logoutJson));

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployerByTokenJson = server.getEmployeeByToken(loginEmployerDtoResponse.getToken());
        assertEquals(getEmployerByTokenJson.getResponseCode(), ERROR_CODE);
    }
}
