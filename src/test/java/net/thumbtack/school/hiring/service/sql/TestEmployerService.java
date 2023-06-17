//package net.thumbtack.school.hiring.service.sql;
//import net.thumbtack.school.hiring.dto.request.*;
//import net.thumbtack.school.hiring.dto.response.*;
//import net.thumbtack.school.hiring.server.ServerResponse;
//import net.thumbtack.school.hiring.utils.MyBatisUtils;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestEmployerService extends TestBase {
//
//    @Test
//    public void testRegisterAndLoginEmployer() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), SUCCESS_CODE);
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
//
//        // получение Employer по токену
//        ServerResponse getEmployerJson = server.getCurrentEmployer(loginEmployerDtoResponse.getToken());
//        GetEmployerDtoResponse getEmployerResponse = GSON.fromJson(getEmployerJson.getResponseData(), GetEmployerDtoResponse.class);
//
//        // проверка данных, введенных при регистрации
//        assertEquals(requestJson.getEmail(), getEmployerResponse.getEmail());
//        assertEquals(requestJson.getLastName(), getEmployerResponse.getLastName());
//        assertEquals(requestJson.getMiddleName(), getEmployerResponse.getMiddleName());
//        assertEquals(requestJson.getFirstName(), getEmployerResponse.getFirstName());
//        assertEquals(requestJson.getLogin(), getEmployerResponse.getLogin());
//    }
//
//    @Test
//    public void testLogoutEmployer() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "InventInc",
//                "просп.Мира д.81/1",
//                "invent_inc.future@gmail.com",
//                "Андреев",
//                "Андреевич",
//                "Андрей",
//                "inventInc.hr",
//                "87686548440"
//        );
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "inventInc.hr",
//                "87686548440"
//        );
//        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
//
//        ServerResponse logoutResponse = server.logoutUser(String.valueOf(loginEmployerDtoResponse.getToken()));
//        assertEquals(logoutResponse.getResponseCode(), SUCCESS_CODE);
//
//        // попытка получить Employer по токену
//        ServerResponse getEmployerByTokenJson = server.getCurrentEmployer(loginEmployerDtoResponse.getToken());
//        assertEquals(getEmployerByTokenJson.getResponseCode(), ERROR_CODE);
//        assertEquals(getEmployerByTokenJson.getResponseData(), "User not exist!");
//    }
//
//    @Test
//    public void testAddAndDeleteVacancy() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
//
//        // запрос на добавление вакансии
//        AddVacancyDtoRequest addVacancyJson0 = new AddVacancyDtoRequest(
//                "Middle",
//                80000
//        );
//
//        // добавление вакансии
//        ServerResponse idJson = server.addVacancy(loginEmployerDtoResponse.getToken(), GSON.toJson(addVacancyJson0));
//        assertEquals(idJson.getResponseCode(), SUCCESS_CODE);
//
//        // получение id вакансии
//        AddVacancyDtoResponse addVacancyResponse = GSON.fromJson(idJson.getResponseData(), AddVacancyDtoResponse.class);
//
//        // получение вакансии по id
//        ServerResponse getVacancyByIdJson = server.getVacancyByIdExternal(
//                loginEmployerDtoResponse.getToken(), addVacancyResponse.getVacancyId());
//        GetVacancyDtoResponse getVacancyDtoResponse = GSON.fromJson(getVacancyByIdJson.getResponseData(), GetVacancyDtoResponse.class);
//
//        // проверка данных вакансии
//        assertEquals(addVacancyJson0.getPosition(), getVacancyDtoResponse.getPosition());
//        assertEquals(addVacancyJson0.getSalary(), getVacancyDtoResponse.getSalary());
//
//
//        // запрос 2 на добавление скилла
//        AddVacancyDtoRequest addVacancyJson1 = new AddVacancyDtoRequest(
//                "Senior",
//                5
//        );
//
//        server.addVacancy(loginEmployerDtoResponse.getToken(), GSON.toJson(addVacancyJson1));
//
//        // получение всех скиллов
//        ServerResponse getAllVacanciesResponse = server.getAllVacancies(loginEmployerDtoResponse.getToken());
//        assertEquals(getAllVacanciesResponse.getResponseCode(), SUCCESS_CODE);
//
//        GetAllVacanciesDtoResponse getAllVacancies = GSON.fromJson(
//                getAllVacanciesResponse.getResponseData(), GetAllVacanciesDtoResponse.class);
//        assertEquals(addVacancyJson0.getPosition(), getAllVacancies.getVacancies().get(0).getPosition());
//        assertEquals(addVacancyJson0.getSalary(), getAllVacancies.getVacancies().get(0).getSalary());
//        assertEquals(addVacancyJson1.getPosition(), getAllVacancies.getVacancies().get(1).getPosition());
//        assertEquals(addVacancyJson1.getSalary(), getAllVacancies.getVacancies().get(1).getSalary());
//
//
//        // запрос на добавление требования
//        AddRequirementDtoRequest addRequirementJson0 = new AddRequirementDtoRequest(
//                addVacancyResponse.getVacancyId(),
//                "Знание Python",
//                4,
//                true
//        );
//
//        // добавление требования
//        ServerResponse idJson1 = server.addVacancyRequirement(
//                loginEmployerDtoResponse.getToken(), GSON.toJson(addRequirementJson0));
//        assertEquals(idJson1.getResponseCode(), SUCCESS_CODE);
//
//        // получение id требования
//        AddRequirementDtoResponse addRequirementDtoResponse = GSON.fromJson(
//                idJson1.getResponseData(), AddRequirementDtoResponse.class);
//
//        // получение требования по id
//        ServerResponse getRequirementByIdJson = server.getRequirementByIdExternal(
//                loginEmployerDtoResponse.getToken(), addRequirementDtoResponse.getRequirementId());
//        GetRequirementDtoResponse getRequirementDtoResponse = GSON.fromJson(
//                getRequirementByIdJson.getResponseData(), GetRequirementDtoResponse.class);
//
//        // проверка данных требования
//        assertEquals(addRequirementJson0.getRequirementName(), getRequirementDtoResponse.getRequirementName());
//        assertEquals(addRequirementJson0.getProfLevel(), getRequirementDtoResponse.getProfLevel());
//        assertEquals(addRequirementJson0.isNecessary(), getRequirementDtoResponse.isNecessary());
//
//        // запрос 2 на добавление требования
//        AddRequirementDtoRequest addRequirementJson1 = new AddRequirementDtoRequest(
//                addVacancyResponse.getVacancyId(),
//                "Знание Swift",
//                3,
//                false
//        );
//
//        server.addVacancyRequirement(loginEmployerDtoResponse.getToken(), GSON.toJson(addRequirementJson1));
//
//        // получение всех скиллов
//        ServerResponse getAllRequirementsResponse = server.getAllRequirements(loginEmployerDtoResponse.getToken());
//        assertEquals(getAllRequirementsResponse.getResponseCode(), SUCCESS_CODE);
//
//        GetAllRequirementsDtoResponse getAllRequirements = GSON.fromJson(
//                getAllRequirementsResponse.getResponseData(), GetAllRequirementsDtoResponse.class);
//        assertEquals(addRequirementJson0.getRequirementName(), getAllRequirements.getRequirementsList().get(0).getRequirementName());
//        assertEquals(addRequirementJson0.getProfLevel(), getAllRequirements.getRequirementsList().get(0).getProfLevel());
//        assertEquals(addRequirementJson1.getRequirementName(), getAllRequirements.getRequirementsList().get(1).getRequirementName());
//        assertEquals(addRequirementJson1.getProfLevel(), getAllRequirements.getRequirementsList().get(1).getProfLevel());
//
//
//        // запрос на удаление требования
//        DeleteRequirementDtoRequest deleteRequirementJson = new DeleteRequirementDtoRequest(
//                addRequirementDtoResponse.getRequirementId()
//        );
//
//        // удаление требования
//        ServerResponse deleteRequirementResponse = server.deleteVacancyRequirement(
//                loginEmployerDtoResponse.getToken(), GSON.toJson(deleteRequirementJson));
//        assertEquals(deleteRequirementResponse.getResponseCode(), SUCCESS_CODE);
//
//        // попытка получения требования по id
//        ServerResponse getRequirementByIdResponse = server.getRequirementByIdExternal(
//                loginEmployerDtoResponse.getToken(), addRequirementDtoResponse.getRequirementId());
//        assertEquals(getRequirementByIdResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(getRequirementByIdResponse.getResponseData(), "Id is invalid!");
//
//        // запрос на удаление вакансии
//        DeleteVacancyDtoRequest deleteVacancyJson = new DeleteVacancyDtoRequest(
//                addVacancyResponse.getVacancyId()
//        );
//
//        // удаление вакансии
//        ServerResponse deleteVacancyResponse = server.deleteVacancy(
//                loginEmployerDtoResponse.getToken(), GSON.toJson(deleteVacancyJson));
//        assertEquals(deleteVacancyResponse.getResponseCode(), SUCCESS_CODE);
//
//        // попытка получения вакансии по id
//        ServerResponse getVacancyByIdJson1 = server.getVacancyByIdExternal(
//                loginEmployerDtoResponse.getToken(), addVacancyResponse.getVacancyId());
//        assertEquals(getVacancyByIdJson1.getResponseCode(), ERROR_CODE);
//        assertEquals(getVacancyByIdJson1.getResponseData(), "Id is invalid!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithEmptyLastName() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Empty last name!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithEmptyFirstName() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Empty first name!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithEmptyMiddleName() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Empty middle name!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithEmptyLogin() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Empty login!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithEmptyPassword() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                ""
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Empty password!");
//    }
//
//    @Test
//    public void testRegisterEmployerWithShortLogin() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hire",
//                "423657801"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Short login!");
//    }
//
//    @Test
//    public void testRegisterEmployeeWithShortPassword() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "4236"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployer(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), ERROR_CODE);
//        assertEquals(actualResponse0.getResponseData(), "Short password!");
//    }
//
//    @Test
//    public void testLoginEmployerWithEmptyLogin() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "",
//                "423657801"
//        );
//
//        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
//        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(loginResponse.getResponseData(), "Empty login!");
//    }
//
//    @Test
//    public void testLoginEmployerWithEmptyPassword() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "hiretool_HRdep",
//                ""
//        );
//
//        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
//        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(loginResponse.getResponseData(), "Empty password!");
//    }
//
//    @Test
//    public void testLoginEmployerWithoutRegister() {
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
//        assertEquals(loginResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(loginResponse.getResponseData(), "Wrong login or password!");
//    }
//
//    @Test
//    public void testAttemptToAddSkillByEmployer() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "HireTool",
//                "ул.Ленина д.19/2",
//                "hiretool.it@gmail.com",
//                "Петров",
//                "Петрович",
//                "Пётр",
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "hiretool_HRdep",
//                "423657801"
//        );
//
//        ServerResponse loginResponse = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(loginResponse.getResponseData(), LoginUserDtoResponse.class);
//
//        AddSkillDtoRequest addSkillJson = new AddSkillDtoRequest(
//                "Язык Java",
//                5
//        );
//
//        // попытка добавления скилла от Employer
//        ServerResponse addSkillResponse = server.addSkill(loginEmployerDtoResponse.getToken(), GSON.toJson(addSkillJson));
//        assertEquals(addSkillResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(addSkillResponse.getResponseData(), "User not exist!");
//    }
//
//    @Test
//    public void testGetCurrentEmployerByEmployeeToken() {
//        RegisterEmployeeDtoRequest requestJson = new RegisterEmployeeDtoRequest(
//                "ivan.ivanov@mail.ru",
//                "Иванов",
//                "Иванович",
//                "Иван",
//                "rocket_ivan",
//                "754376579"
//        );
//
//        ServerResponse actualResponse0 = server.registerEmployee(GSON.toJson(requestJson));
//        assertEquals(actualResponse0.getResponseCode(), SUCCESS_CODE);
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "rocket_ivan",
//                "754376579"
//        );
//
//        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployeeDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
//
//        // попытка получить Employer по токену Employee
//        ServerResponse getEmployerByTokenJson = server.getCurrentEmployer(loginEmployeeDtoResponse.getToken());
//        assertEquals(getEmployerByTokenJson.getResponseCode(), ERROR_CODE);
//        assertEquals(getEmployerByTokenJson.getResponseData(), "User not exist!");
//    }
//
//    @Test
//    public void testGetAllVacanciesOrRequirementsWithoutAdding() {
//        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
//                "InventInc",
//                "просп.Мира д.81/1",
//                "invent_inc.future@gmail.com",
//                "Андреев",
//                "Андреевич",
//                "Андрей",
//                "inventInc.hr",
//                "87686548440"
//        );
//        server.registerEmployer(GSON.toJson(requestJson));
//
//        LoginUserDtoRequest loginJson = new LoginUserDtoRequest(
//                "inventInc.hr",
//                "87686548440"
//        );
//        ServerResponse tokenJson = server.loginUser(GSON.toJson(loginJson));
//        LoginUserDtoResponse loginEmployerDtoResponse = GSON.fromJson(tokenJson.getResponseData(), LoginUserDtoResponse.class);
//
//        // попытка получения всех вакансий
//        ServerResponse getAllVacanciesResponse = server.getAllVacancies(loginEmployerDtoResponse.getToken());
//        assertEquals(getAllVacanciesResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(getAllVacanciesResponse.getResponseData(), "Error getting vacancies!");
//
//        // попытка получения всех требований
//        ServerResponse getAllRequirementsResponse = server.getAllRequirements(loginEmployerDtoResponse.getToken());
//        assertEquals(getAllRequirementsResponse.getResponseCode(), ERROR_CODE);
//        assertEquals(getAllRequirementsResponse.getResponseData(), "Error getting requirements!");
//    }
//}