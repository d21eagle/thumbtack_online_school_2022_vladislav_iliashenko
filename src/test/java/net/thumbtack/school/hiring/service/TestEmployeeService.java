package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEmployeeService extends TestBase {
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

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        ServerResponse getEmployeeByTokenJson = server.getEmployeeByToken(loginEmployeeDtoResponse.getToken());
        GetEmployeeDtoResponse getEmployeeResponse = GSON.fromJson(getEmployeeByTokenJson.getResponseData(), GetEmployeeDtoResponse.class);

        assertEquals(requestJson.getEmail(), getEmployeeResponse.getEmail());
        assertEquals(requestJson.getLastName(), getEmployeeResponse.getLastName());
        assertEquals(requestJson.getMiddleName(), getEmployeeResponse.getMiddleName());
        assertEquals(requestJson.getFirstName(), getEmployeeResponse.getFirstName());
        assertEquals(requestJson.getLogin(), getEmployeeResponse.getLogin());
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

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "petr_one",
                "982360301"
        );
        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        LogoutUserDtoRequest logoutJson = new LogoutUserDtoRequest(loginEmployeeDtoResponse.getToken());
        ServerResponse logoutResponse = server.logoutUser(GSON.toJson(logoutJson));

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployeeByTokenJson = server.getEmployeeByToken(loginEmployeeDtoResponse.getToken());
        assertEquals(getEmployeeByTokenJson.getResponseCode(), ERROR_CODE);
    }

    @Test
    public void testAddAndDeleteEmployeeSkill() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );
        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        AddSkillDtoRequest addSkillJson = new AddSkillDtoRequest(
                "Язык Java",
                5
        );
        // добавление скилла
        ServerResponse idJson = server.addSkill(loginEmployeeDtoResponse.getToken(), GSON.toJson(addSkillJson));
        assertEquals(idJson.getResponseCode(), SUCCESS_CODE);

        // получение id скилла
        AddSkillDtoResponse addSkillResponse = GSON.fromJson(idJson.getResponseData(), AddSkillDtoResponse.class);

        // получение скилла по id
        ServerResponse getSkillByIdJson = server.getSkillById(addSkillResponse.getId());
        GetSkillDtoResponse getSkillDtoResponse = GSON.fromJson(getSkillByIdJson.getResponseData(), GetSkillDtoResponse.class);

        assertEquals(addSkillJson.getSkillName(), getSkillDtoResponse.getSkillName());
        assertEquals(addSkillJson.getProfLevel(), getSkillDtoResponse.getProfLevel());

        DeleteSkillDtoRequest deleteSkillJson = new DeleteSkillDtoRequest(
                addSkillResponse.getId()
        );

        // удаление скилла по id
        ServerResponse deleteSkillResponse = server.deleteSkillById(GSON.toJson(deleteSkillJson));
        assertEquals(deleteSkillResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getSkillByIdJson1 = server.getSkillById(addSkillResponse.getId());
        assertEquals(getSkillByIdJson1.getResponseCode(), ERROR_CODE);
    }
}
