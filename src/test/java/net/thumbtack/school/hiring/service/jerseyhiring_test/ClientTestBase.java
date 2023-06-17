package net.thumbtack.school.hiring.service.jerseyhiring_test;

import net.thumbtack.school.hiring.client.HiringClient;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.server.HiringServer;
import net.thumbtack.school.hiring.server.config.ServerSettings;
import net.thumbtack.school.hiring.service.DebugService;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientTestBase {
    private final DebugService debugService = new DebugService();
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTestBase.class);
    private static final Settings settings = Settings.getInstance();
    protected static HiringClient hiringClient = new HiringClient();
    private static String baseURL;

    private static void setBaseUrl() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.debug("Can't determine my own host name", e);
        }
        baseURL = "http://" + hostName + ":" + ServerSettings.getRestHTTPPort() + "/api";
    }

    @BeforeAll
    public static void setUp() {
        MyBatisUtils.initSqlSessionFactory();
        settings.setDatabaseType("SQL");
    }

    @BeforeAll
    public static void startServer() {
        setBaseUrl();
        HiringServer.createServer();
    }

    @AfterAll
    public static void stopServer() {
        HiringServer.stopServer();
    }

    @BeforeEach
    public void clearDataBase() {
        debugService.clear();
    }

    public static String getBaseURL() {
        return baseURL;
    }

    protected void checkFailureResponse(Object response, ServerErrorCode expectedStatus) {
        assertTrue(response instanceof FailureResponse);
        FailureResponse failureResponseObject = (FailureResponse) response;
        assertEquals(expectedStatus, failureResponseObject.getErrorCode());
    }

    protected void registerEmployee(String email, String lastName, String middleName, String firstName, String login, String password, ServerErrorCode expectedStatus) {
        RegisterEmployeeDtoRequest request = new RegisterEmployeeDtoRequest(email, lastName, middleName, firstName, login, password);
        Object response = hiringClient.post(baseURL + "/hiring/employee", request, RegisterEmployeeDtoResponse.class, "");
        if (response instanceof RegisterEmployeeDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void registerEmployer(String companyName, String companyAddress, String email, String lastName, String middleName, String firstName, String login, String password, ServerErrorCode expectedStatus) {
        RegisterEmployerDtoRequest request = new RegisterEmployerDtoRequest(companyName, companyAddress, email, lastName, middleName, firstName, login, password);
        Object response = hiringClient.post(baseURL + "/hiring/employer", request, RegisterEmployerDtoResponse.class, "");
        if (response instanceof RegisterEmployerDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected LoginUserDtoResponse loginUser(String login, String password, ServerErrorCode expectedStatus) {
        LoginUserDtoRequest request = new LoginUserDtoRequest(login, password);
        Object response = hiringClient.post(baseURL + "/hiring/user/login", request, LoginUserDtoResponse.class, "");
        if (response instanceof LoginUserDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
            return (LoginUserDtoResponse) response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected void logoutUser(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.delete(baseURL + "/hiring/user/logout/" + token, EmptyResponse.class, token);
        if (response instanceof EmptyResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected AddSkillDtoResponse addSkill(String token, String skillName, int profLevel, ServerErrorCode expectedStatus) {
        AddSkillDtoRequest addSkillDtoRequest = new AddSkillDtoRequest(skillName, profLevel);
        Object response = hiringClient.post(baseURL + "/hiring/employee/skill", addSkillDtoRequest, AddSkillDtoResponse.class, token);
        if (response instanceof AddSkillDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
            return (AddSkillDtoResponse) response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected void deleteSkill(String token, int skillId, ServerErrorCode expectedStatus) {
        Object response = hiringClient.delete(baseURL + "/hiring/employee/skill/" + skillId, EmptyResponse.class, token);
        if (response instanceof EmptyResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected AddVacancyDtoResponse addVacancy(String token, String position, int salary, ServerErrorCode expectedStatus) {
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(position, salary);
        Object response = hiringClient.post(baseURL + "/hiring/employer/vacancy", addVacancyDtoRequest, AddVacancyDtoResponse.class, token);
        if (response instanceof AddVacancyDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
            return (AddVacancyDtoResponse) response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected AddRequirementDtoResponse addVacancyRequirement(String token, int vacancyId, String requirementName, int profLevel, boolean isNecessary, ServerErrorCode expectedStatus) {
        AddRequirementDtoRequest addRequirementDtoRequest = new AddRequirementDtoRequest(vacancyId, requirementName, profLevel, isNecessary);
        Object response = hiringClient.post(baseURL + "/hiring/employer/vacancy/vacancy_requirement", addRequirementDtoRequest, AddRequirementDtoResponse.class, token);
        if (response instanceof AddRequirementDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
            return (AddRequirementDtoResponse) response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected void deleteVacancy(String token, int vacancyId, ServerErrorCode expectedStatus) {
        Object response = hiringClient.delete(baseURL + "/hiring/employer/vacancy/" + vacancyId, EmptyResponse.class, token);
        if (response instanceof EmptyResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void deleteVacancyRequirement(String token, int requirementId, ServerErrorCode expectedStatus) {
        Object response = hiringClient.delete(baseURL + "/hiring/employer/vacancy/vacancy_requirement/" + requirementId, EmptyResponse.class, token);
        if (response instanceof EmptyResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getCurrentEmployee(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employee/" + token, GetEmployeeDtoResponse.class, token);
        if (response instanceof GetEmployeeDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getCurrentEmployer(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employer/" + token, GetEmployerDtoResponse.class, token);
        if (response instanceof GetEmployerDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getSkillByIdExternal(String token, int idSkill, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employee/" + token + "/" + idSkill, GetSkillDtoResponse.class, token);
        if (response instanceof GetSkillDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getAllSkills(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employee/" + token + "/skills", GetAllSkillsDtoResponse.class, token);
        if (response instanceof GetAllSkillsDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getVacancyByIdExternal(String token, int idVacancy, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employer/" + token + "/" + idVacancy, GetVacancyDtoResponse.class, token);
        if (response instanceof GetVacancyDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getRequirementByIdExternal(String token, int idRequirement, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employer/" + token + "/vacancy/" + idRequirement, GetRequirementDtoResponse.class, token);
        if (response instanceof GetRequirementDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getAllVacancies(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employer/" + token + "/vacancies", GetAllVacanciesDtoResponse.class, token);
        if (response instanceof GetAllVacanciesDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }

    protected void getAllRequirements(String token, ServerErrorCode expectedStatus) {
        Object response = hiringClient.get(baseURL + "/hiring/employer/" + token + "/vacancies/vacancy_requirement", GetAllRequirementsDtoResponse.class, token);
        if (response instanceof GetAllRequirementsDtoResponse) {
            assertEquals(ServerErrorCode.SUCCESS, expectedStatus);
        } else {
            checkFailureResponse(response, expectedStatus);
        }
    }
}
