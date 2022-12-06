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

        ServerResponse getEmployeeByTokenJson = server.getCurrentEmployee(loginEmployeeDtoResponse.getToken());
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

        ServerResponse logoutResponse = server.logoutUser(loginEmployeeDtoResponse.getToken());

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployeeByTokenJson = server.getCurrentEmployee(loginEmployeeDtoResponse.getToken());
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
        RegisterEmployeeDtoResponse employeeDtoResponse = GSON.fromJson(actualResponse0.getResponseData(), RegisterEmployeeDtoResponse.class);

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579");

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
        ServerResponse getSkillByIdJson = server.getSkillByIdExternal(
                loginEmployeeDtoResponse.getToken(), addSkillResponse.getSkillId());
        GetSkillDtoResponse getSkillDtoResponse = GSON.fromJson(getSkillByIdJson.getResponseData(), GetSkillDtoResponse.class);

        assertEquals(addSkillJson.getSkillName(), getSkillDtoResponse.getSkillName());
        assertEquals(addSkillJson.getProfLevel(), getSkillDtoResponse.getProfLevel());

        DeleteSkillDtoRequest deleteSkillJson = new DeleteSkillDtoRequest(
                employeeDtoResponse.getUserId(),
                addSkillResponse.getSkillId()
        );

        // удаление скилла
        ServerResponse deleteSkillResponse = server.deleteSkill(
                loginEmployeeDtoResponse.getToken(), GSON.toJson(deleteSkillJson));
        assertEquals(deleteSkillResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getSkillByIdJson1 = server.getSkillByIdExternal(
                loginEmployeeDtoResponse.getToken(), addSkillResponse.getSkillId());
        assertEquals(getSkillByIdJson1.getResponseCode(), ERROR_CODE);
    }

    @Test
    public void testRegisterEmployeeWithEmptyLastName() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty last name!");
    }

    @Test
    public void testRegisterEmployeeWithEmptyFirstName() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty first name!");
    }

    @Test
    public void testRegisterEmployeeWithEmptyMiddleName() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty middle name!");
    }

    @Test
    public void testRegisterEmployeeWithEmptyLogin() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty login!");
    }

    @Test
    public void testRegisterEmployeeWithEmptyPassword() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                ""
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty password!");
    }

    @Test
    public void testRegisterEmployeeWithShortLogin() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rock",
                "754376579"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Short login!");
    }

    @Test
    public void testRegisterEmployeeWithShortPassword() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "7543"
        );

        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Short password!");
    }

    @Test
    public void testLoginEmployeeWithEmptyLogin() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse regResponse = server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "",
                "754376579"
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Empty login!");
    }

    @Test
    public void testLoginEmployeeWithEmptyPassword() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse regResponse = server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                ""
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Empty password!");
    }

    @Test
    public void testAttemptToAddVacancyByEmployee() {
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

        AddVacancyDtoRequest addVacancyJson = new AddVacancyDtoRequest(
                "middle",
                80000
        );

        ServerResponse addVacancyResponse = server.addVacancy(loginEmployeeDtoResponse.getToken(), GSON.toJson(addVacancyJson));
        assertEquals(addVacancyResponse.getResponseCode(), ERROR_CODE);
        assertEquals(addVacancyResponse.getResponseData(), "Usertype is wrong!");
    }

    @Test
    public void testAttemptToAddVacancyRequirementByEmployee() {
        // Операции для Employer
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
        RegisterEmployerDtoResponse employerDtoResponse = GSON.fromJson(actualResponse0.getResponseData(), RegisterEmployerDtoResponse.class);;

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
        GetEmployerDtoResponse employerResponse = GSON.fromJson(tokenJson.getResponseData(), GetEmployerDtoResponse.class);

        // запрос на добавление вакансии
        AddVacancyDtoRequest addVacancyJson = new AddVacancyDtoRequest(
                "middle",
                80000
        );

        // добавление вакансии
        ServerResponse idJson = server.addVacancy(loginEmployerDtoResponse.getToken(), GSON.toJson(addVacancyJson));
        assertEquals(idJson.getResponseCode(), SUCCESS_CODE);

        // получение id вакансии
        AddVacancyDtoResponse addVacancyResponse = GSON.fromJson(idJson.getResponseData(), AddVacancyDtoResponse.class);


        // Операции для Employee
        RegisterEmployeeDtoRequest regEmployeeRequest = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        ServerResponse regEmployeeResponse = server.registerEmployee(GSON.toJson(regEmployeeRequest));

        LoginUserDtoRequest loginEmployeeRequest = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse loginEmployeeResponse = server.loginUser(GSON.toJson(loginEmployeeRequest));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(loginEmployeeResponse.getResponseData(), LoginUserDtoResponse.class);

        AddRequirementDtoRequest addRequirementJson = new AddRequirementDtoRequest(
                addVacancyResponse.getVacancyId(),
                "Soft skills",
                5,
                true
        );

        ServerResponse addVacancyRequirement = server.addVacancyRequirement(
                loginEmployeeDtoResponse.getToken(), GSON.toJson(addRequirementJson));
        assertEquals(addVacancyRequirement.getResponseCode(), ERROR_CODE);
        assertEquals(addVacancyRequirement.getResponseData(), "Usertype is wrong!");
    }
}
