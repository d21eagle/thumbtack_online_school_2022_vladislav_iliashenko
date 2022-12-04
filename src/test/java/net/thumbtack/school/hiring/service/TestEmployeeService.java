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
        ServerResponse skillResponse0 = server.addEmployeeSkill(loginEmployeeDtoResponse.getToken(), GSON.toJson(addSkillJson));
        assertEquals(skillResponse0.getResponseCode(), SUCCESS_CODE);

        // получение employee по токену
        ServerResponse getEmployeeByTokenJson = server.getEmployeeByToken(loginEmployeeDtoResponse.getToken());
        GetEmployeeDtoResponse getEmployeeResponse = GSON.fromJson(getEmployeeByTokenJson.getResponseData(), GetEmployeeDtoResponse.class);

        assertEquals(addSkillJson.getSkillName(), getEmployeeResponse.getSkills().get(0).getSkillName());
        assertEquals(addSkillJson.getProfLevel(), getEmployeeResponse.getSkills().get(0).getProfLevel());

        DeleteSkillDtoRequest deleteSkillJson = new DeleteSkillDtoRequest(
                getEmployeeResponse.getSkills().get(0).getId()
        );

        ServerResponse skillResponse1 = server.deleteEmployeeSkillById(GSON.toJson(deleteSkillJson));
        assertEquals(skillResponse1.getResponseCode(), SUCCESS_CODE);
    }
}
