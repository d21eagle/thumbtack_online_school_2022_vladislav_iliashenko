package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEmployerService extends TestBase {
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

        ServerResponse getEmployerJson = server.getCurrentEmployer(loginEmployerDtoResponse.getToken());
        GetEmployerDtoResponse getEmployerResponse = GSON.fromJson(getEmployerJson.getResponseData(), GetEmployerDtoResponse.class);

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

        ServerResponse logoutResponse = server.logoutUser(loginEmployerDtoResponse.getToken());

        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployerByTokenJson = server.getCurrentEmployee(loginEmployerDtoResponse.getToken());
        assertEquals(getEmployerByTokenJson.getResponseCode(), ERROR_CODE);
    }

    @Test
    public void testAddAndDeleteVacancy() {
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

        // получение вакансии по id
        ServerResponse getVacancyByIdJson = server.getVacancyByIdExternal(
                loginEmployerDtoResponse.getToken(), addVacancyResponse.getVacancyId());
        GetVacancyDtoResponse getVacancyDtoResponse = GSON.fromJson(getVacancyByIdJson.getResponseData(), GetVacancyDtoResponse.class);

        assertEquals(addVacancyJson.getPosition(), getVacancyDtoResponse.getPosition());
        assertEquals(addVacancyJson.getSalary(), getVacancyDtoResponse.getSalary());

        // запрос на добавление требования
        AddRequirementDtoRequest addRequirementJson = new AddRequirementDtoRequest(
                addVacancyResponse.getVacancyId(),
                "Знание Python",
                4,
                true
        );

        // добавление требования
        ServerResponse idJson1 = server.addVacancyRequirement(
                loginEmployerDtoResponse.getToken(), GSON.toJson(addRequirementJson));
        assertEquals(idJson1.getResponseCode(), SUCCESS_CODE);

        // получение id требования
        AddRequirementDtoResponse addRequirementDtoResponse = GSON.fromJson(
                idJson1.getResponseData(), AddRequirementDtoResponse.class);

        // получение требования по id
        ServerResponse getRequirementByIdJson = server.getRequirementByIdExternal(
                loginEmployerDtoResponse.getToken(), addRequirementDtoResponse.getRequirementId());
        GetRequirementDtoResponse getRequirementDtoResponse = GSON.fromJson(
                getRequirementByIdJson.getResponseData(), GetRequirementDtoResponse.class);

        assertEquals(addRequirementJson.getRequirementName(), getRequirementDtoResponse.getRequirementName());
        assertEquals(addRequirementJson.getProfLevel(), getRequirementDtoResponse.getProfLevel());
        assertEquals(addRequirementJson.isNecessary(), getRequirementDtoResponse.isNecessary());

        // запрос на удаление требования
        DeleteRequirementDtoRequest deleteRequirementJson = new DeleteRequirementDtoRequest(
                addVacancyResponse.getVacancyId(),
                addRequirementDtoResponse.getRequirementId()
        );

        // удаление требования
        ServerResponse deleteRequirementResponse = server.deleteVacancyRequirement(
                loginEmployerDtoResponse.getToken(), GSON.toJson(deleteRequirementJson));
        assertEquals(deleteRequirementResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getRequirementByIdResponse = server.getRequirementByIdExternal(
                loginEmployerDtoResponse.getToken(), addRequirementDtoResponse.getRequirementId());
        assertEquals(getRequirementByIdResponse.getResponseCode(), ERROR_CODE);

        // запрос на удаление вакансии
        DeleteVacancyDtoRequest deleteVacancyJson = new DeleteVacancyDtoRequest(
                employerDtoResponse.getUserId(),
                addVacancyResponse.getVacancyId()
        );

        // удаление вакансии
        ServerResponse deleteVacancyResponse = server.deleteVacancy(
                loginEmployerDtoResponse.getToken(), GSON.toJson(deleteVacancyJson));
        assertEquals(deleteVacancyResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getVacancyByIdJson1 = server.getVacancyByIdExternal(
                loginEmployerDtoResponse.getToken(), addVacancyResponse.getVacancyId());
        assertEquals(getVacancyByIdJson1.getResponseCode(), ERROR_CODE);
    }

    @Test
    public void testRegisterEmployerWithEmptyLastName() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty last name!");
    }

    @Test
    public void testRegisterEmployerWithEmptyFirstName() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty first name!");
    }

    @Test
    public void testRegisterEmployerWithEmptyMiddleName() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "",
                "Пётр",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty middle name!");
    }

    @Test
    public void testRegisterEmployerWithEmptyLogin() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty login!");
    }

    @Test
    public void testRegisterEmployerWithEmptyPassword() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                ""
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Empty password!");
    }

    @Test
    public void testRegisterEmployerWithShortLogin() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hire",
                "423657801"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Short login!");
    }

    @Test
    public void testRegisterEmployeeWithShortPassword() {
        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Петрович",
                "Пётр",
                "hiretool_HRdep",
                "4236"
        );

        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse0.getResponseData(), "Short password!");
    }

    @Test
    public void testLoginEmployerWithEmptyLogin() {
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

        ServerResponse regResponse = server.registerEmployer(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "",
                "423657801"
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Empty login!");
    }

    @Test
    public void testLoginEmployerWithEmptyPassword() {
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

        ServerResponse regResponse = server.registerEmployer(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                ""
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
        assertEquals(loginResponse.getResponseData(), "Empty password!");
    }

    @Test
    public void testAttemptToAddSkillByEmployer() {
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

        ServerResponse regResponse = server.registerEmployer(GSON.toJson(requestJson));

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(loginResponse.getResponseData(), LoginUserDtoResponse.class);

        AddSkillDtoRequest addSkillJson = new AddSkillDtoRequest(
                "Язык Java",
                5
        );

        ServerResponse addSkillResponse = server.addSkill(loginEmployerDtoResponse.getToken(), GSON.toJson(addSkillJson));
        assertEquals(addSkillResponse.getResponseCode(), ERROR_CODE);
        assertEquals(addSkillResponse.getResponseData(), "Usertype is wrong!");
    }
}
