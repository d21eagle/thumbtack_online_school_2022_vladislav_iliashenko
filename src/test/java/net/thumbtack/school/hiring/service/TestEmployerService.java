package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.thumbtack.school.hiring.mapper.EmployerMapper;

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

        LogoutUserDtoRequest logoutJson = new LogoutUserDtoRequest(loginEmployerDtoResponse.getToken());
        ServerResponse logoutResponse = server.logoutUser(GSON.toJson(logoutJson));

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

        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
        GetEmployerDtoResponse employerResponse = GSON.fromJson(tokenJson.getResponseData(), GetEmployerDtoResponse.class);

        // запрос на добавление вакансии
        AddVacancyDtoRequest addVacancyJson = new AddVacancyDtoRequest(
                EmployerMapper.INSTANCE.getEmployer(employerResponse),
                "middle",
                80000
        );

        // добавление вакансии
        ServerResponse idJson = server.addVacancy(loginEmployeeDtoResponse.getToken(), GSON.toJson(addVacancyJson));
        assertEquals(idJson.getResponseCode(), SUCCESS_CODE);

        // получение id вакансии
        AddVacancyDtoResponse addVacancyResponse = GSON.fromJson(idJson.getResponseData(), AddVacancyDtoResponse.class);

        // получение вакансии по id
        ServerResponse getVacancyByIdJson = server.getCurrentVacancy(addVacancyResponse.getId());
        GetVacancyDtoResponse getVacancyDtoResponse = GSON.fromJson(getVacancyByIdJson.getResponseData(), GetVacancyDtoResponse.class);

        assertEquals(addVacancyJson.getEmployer(), getVacancyDtoResponse.getEmployer());
        assertEquals(addVacancyJson.getPosition(), getVacancyDtoResponse.getPosition());
        assertEquals(addVacancyJson.getSalary(), getVacancyDtoResponse.getSalary());

        // запрос на добавление требования
        AddEmployeeRequirementDtoRequest addEmployeeRequirementJson = new AddEmployeeRequirementDtoRequest(
                addVacancyResponse.getId(),
                "Знание Python",
                4,
                true
        );

        // добавление требования
        ServerResponse idJson1 = server.addEmployeeRequirement(GSON.toJson(addEmployeeRequirementJson));
        assertEquals(idJson1.getResponseCode(), SUCCESS_CODE);

        // получение id требования
        AddEmployeeRequirementDtoResponse addEmployeeRequirementDtoResponse = GSON.fromJson(
                idJson1.getResponseData(), AddEmployeeRequirementDtoResponse.class);

        // получение требования по id
        ServerResponse getRequirementByIdJson = server.getCurrentEmployeeRequirement(addEmployeeRequirementDtoResponse.getId());
        GetEmployeeRequirementDtoResponse getRequirementDtoResponse = GSON.fromJson(
                getRequirementByIdJson.getResponseData(), GetEmployeeRequirementDtoResponse.class);

        assertEquals(addEmployeeRequirementJson.getRequirementName(), getRequirementDtoResponse.getRequirementName());
        assertEquals(addEmployeeRequirementJson.getProfLevel(), getRequirementDtoResponse.getProfLevel());
        assertEquals(addEmployeeRequirementJson.isNecessary(), getRequirementDtoResponse.isNecessary());

        // запрос на удаление требования
        DeleteEmployeeRequirementDtoRequest deleteRequirementJson = new DeleteEmployeeRequirementDtoRequest(
                addEmployeeRequirementDtoResponse.getId()
        );

        // удаление требования по id
        ServerResponse deleteRequirementResponse = server.deleteEmployeeRequirementById(GSON.toJson(deleteRequirementJson));
        assertEquals(deleteRequirementResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getEmployeeRequirementByIdJson = server.getCurrentEmployeeRequirement(addEmployeeRequirementDtoResponse.getId());
        assertEquals(getEmployeeRequirementByIdJson.getResponseCode(), ERROR_CODE);

        // запрос на удаление вакансии
        DeleteVacancyDtoRequest deleteVacancyJson = new DeleteVacancyDtoRequest(
                addVacancyResponse.getId()
        );

        // удаление вакансии по id
        ServerResponse deleteVacancyResponse = server.deleteVacancy(GSON.toJson(deleteVacancyJson));
        assertEquals(deleteVacancyResponse.getResponseCode(), SUCCESS_CODE);

        ServerResponse getVacancyByIdJson1 = server.getCurrentVacancy(addVacancyResponse.getId());
        assertEquals(getVacancyByIdJson1.getResponseCode(), ERROR_CODE);
    }
}
