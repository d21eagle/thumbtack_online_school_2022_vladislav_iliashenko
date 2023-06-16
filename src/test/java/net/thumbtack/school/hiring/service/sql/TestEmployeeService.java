package net.thumbtack.school.hiring.service.sql;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.junit.jupiter.api.BeforeAll;
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

        // получение Employee по токену
        ServerResponse getEmployeeByTokenJson = server.getCurrentEmployee(loginEmployeeDtoResponse.getToken());
        GetEmployeeDtoResponse getEmployeeResponse = GSON.fromJson(getEmployeeByTokenJson.getResponseData(), GetEmployeeDtoResponse.class);

        // проверка данных, введенных при регистрации
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
        server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "petr_one",
                "982360301"
        );
        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        ServerResponse logoutResponse = server.logoutUser(String.valueOf(loginEmployeeDtoResponse.getToken()));
        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        // попытка получить Employee по токену
        ServerResponse getEmployeeByTokenJson = server.getCurrentEmployee(loginEmployeeDtoResponse.getToken());
        assertEquals(getEmployeeByTokenJson.getResponseCode(), ERROR_CODE);
        assertEquals(getEmployeeByTokenJson.getResponseData(), "User not exist!");
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

        server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579");

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        // запрос 1 на добавление скилла
        AddSkillDtoRequest addSkillJson0 = new AddSkillDtoRequest(
                "Язык Java",
                5
        );

        // добавление скилла
        ServerResponse addSkillResponse0 = server.addSkill(loginEmployeeDtoResponse.getToken(), GSON.toJson(addSkillJson0));
        assertEquals(addSkillResponse0.getResponseCode(), SUCCESS_CODE);

        // получение id скилла
        AddSkillDtoResponse addSkillResponse = GSON.fromJson(addSkillResponse0.getResponseData(), AddSkillDtoResponse.class);

        // получение скилла по id
        ServerResponse getSkillByIdJson = server.getSkillByIdExternal(
                loginEmployeeDtoResponse.getToken(), addSkillResponse.getSkillId());

        GetSkillDtoResponse getSkillDtoResponse = GSON.fromJson(getSkillByIdJson.getResponseData(), GetSkillDtoResponse.class);

        // проверка данных скилла
        assertEquals(addSkillJson0.getSkillName(), getSkillDtoResponse.getSkillName());
        assertEquals(addSkillJson0.getProfLevel(), getSkillDtoResponse.getProfLevel());


        // запрос 2 на добавление скилла
        AddSkillDtoRequest addSkillJson1 = new AddSkillDtoRequest(
                "Язык C#",
                4
        );

        server.addSkill(loginEmployeeDtoResponse.getToken(), GSON.toJson(addSkillJson1));

        // получение всех скиллов
        ServerResponse getAllSkillsResponse = server.getAllSkills(loginEmployeeDtoResponse.getToken());
        assertEquals(getAllSkillsResponse.getResponseCode(), SUCCESS_CODE);

        GetAllSkillsDtoResponse getAllSkills = GSON.fromJson(
                getAllSkillsResponse.getResponseData(), GetAllSkillsDtoResponse.class);
        assertEquals(addSkillJson0.getSkillName(), getAllSkills.getSkills().get(0).getSkillName());
        assertEquals(addSkillJson0.getProfLevel(), getAllSkills.getSkills().get(0).getProfLevel());
        assertEquals(addSkillJson1.getSkillName(), getAllSkills.getSkills().get(1).getSkillName());
        assertEquals(addSkillJson1.getProfLevel(), getAllSkills.getSkills().get(1).getProfLevel());


        // запрос на удаление скилла
        DeleteSkillDtoRequest deleteSkillJson = new DeleteSkillDtoRequest(
                addSkillResponse.getSkillId()
        );

        // удаление скилла
        ServerResponse deleteSkillResponse = server.deleteSkill(
                loginEmployeeDtoResponse.getToken(), GSON.toJson(deleteSkillJson));
        assertEquals(deleteSkillResponse.getResponseCode(), SUCCESS_CODE);

        // попытка получения скилла по id
        ServerResponse getSkillByIdJson1 = server.getSkillByIdExternal(
                loginEmployeeDtoResponse.getToken(), addSkillResponse.getSkillId());
        assertEquals(getSkillByIdJson1.getResponseCode(), ERROR_CODE);
        assertEquals(getSkillByIdJson1.getResponseData(), "Id is invalid!");
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

        server.registerEmployee(GSON.toJson(requestJson));

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

        server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                ""
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Empty password!");
    }

    @Test
    public void testLoginEmployeeWithoutRegister() {
        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Wrong login or password!");
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

        server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        // попытка Employee добавить вакансии
        AddVacancyDtoRequest addVacancyJson = new AddVacancyDtoRequest(
                "middle",
                80000
        );

        ServerResponse addVacancyResponse = server.addVacancy(loginEmployeeDtoResponse.getToken(), GSON.toJson(addVacancyJson));
        assertEquals(addVacancyResponse.getResponseCode(), ERROR_CODE);
        assertEquals(addVacancyResponse.getResponseData(), "User not exist!");
    }

    @Test
    public void testAttemptToAddVacancyRequirementByEmployee() {
        // *Employer добавляет вакансию*
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

        server.registerEmployer(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

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

        // *Employee пытается добавить требования к вакансии*
        RegisterEmployeeDtoRequest regEmployeeRequest = new RegisterEmployeeDtoRequest(
                "ivan.ivanov@mail.ru",
                "Иванов",
                "Иванович",
                "Иван",
                "rocket_ivan",
                "754376579"
        );

        server.registerEmployee(GSON.toJson(regEmployeeRequest));

        LoginUserDtoRequest loginEmployeeRequest = new LoginUserDtoRequest(
                "rocket_ivan",
                "754376579"
        );

        ServerResponse loginEmployeeResponse = server.loginUser(GSON.toJson(loginEmployeeRequest));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(loginEmployeeResponse.getResponseData(), LoginUserDtoResponse.class);

        // запрос на добавление требований
        AddRequirementDtoRequest addRequirementJson = new AddRequirementDtoRequest(
                addVacancyResponse.getVacancyId(),
                "Soft skills",
                5,
                true
        );

        ServerResponse addVacancyRequirement = server.addVacancyRequirement(
                loginEmployeeDtoResponse.getToken(), GSON.toJson(addRequirementJson));
        assertEquals(addVacancyRequirement.getResponseCode(), ERROR_CODE);
        assertEquals(addVacancyRequirement.getResponseData(), "User not exist!");
    }

    @Test
    public void testGetCurrentEmployeeByEmployerToken() {
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

        server.registerEmployer(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        // попытка получить Employee по токену Employer
        ServerResponse getEmployeeByEmployerTokenJson = server.getCurrentEmployee(loginEmployerDtoResponse.getToken());
        assertEquals(getEmployeeByEmployerTokenJson.getResponseCode(), ERROR_CODE);
        assertEquals(getEmployeeByEmployerTokenJson.getResponseData(), "User not exist!");
    }

    @Test
    public void testGetAllSkillsWithoutAdding() {
        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
                "petr.petrov@mail.ru",
                "Петров",
                "Петрович",
                "Пётр",
                "petr_one",
                "982360301"
        );
        server.registerEmployee(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "petr_one",
                "982360301"
        );
        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);

        // попытка получения всех скиллов
        ServerResponse getAllSkillsResponse = server.getAllSkills(loginEmployeeDtoResponse.getToken());
        assertEquals(getAllSkillsResponse.getResponseCode(), ERROR_CODE);
        assertEquals(getAllSkillsResponse.getResponseData(), "Error getting skills!");
    }
}
