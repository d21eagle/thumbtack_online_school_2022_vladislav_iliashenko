package net.thumbtack.school.hiring.server;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.service.*;
import java.util.UUID;

public class Server {

    private final EmployeeService employeeService = new EmployeeService();
    private final EmployerService employerService = new EmployerService();
    private final UserService userService = new UserService();
    private final DebugService debugService = new DebugService();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        return employeeService.registerEmployee(requestJson);
    }
    public ServerResponse registerEmployer(String requestJson) throws JsonSyntaxException {
        return employerService.registerEmployer(requestJson);
    }

    public ServerResponse loginUser(String requestJson) {
        return userService.loginUser(requestJson);
    }

    public ServerResponse logoutUser(String token) {
        return userService.logoutUser(token);
    }

    public ServerResponse addSkill(UUID token, String requestJson) {
        return employeeService.addSkill(token, requestJson);
    }

    public ServerResponse deleteSkill(UUID token, String requestJson) {
        return employeeService.deleteSkill(token, requestJson);
    }

    public ServerResponse addVacancy(UUID token, String requestJson) {
        return employerService.addVacancy(token, requestJson);
    }

    public ServerResponse addVacancyRequirement(UUID token, String requestJson) {
        return employerService.addVacancyRequirement(token, requestJson);
    }

    public ServerResponse deleteVacancy(UUID token, String requestJson) {
        return employerService.deleteVacancy(token, requestJson);
    }

    public ServerResponse deleteVacancyRequirement(UUID token, String requestJson) {
        return employerService.deleteVacancyRequirement(token, requestJson);
    }

    public ServerResponse getCurrentEmployee(UUID token) {
        return employeeService.getCurrentEmployee(token);
    }

    public ServerResponse getCurrentEmployer(UUID token) {
        return employerService.getCurrentEmployer(token);
    }

    public ServerResponse getSkillByIdExternal(UUID token, int id) {
        return employeeService.getSkillByIdExternal(token, id);
    }

    public ServerResponse getAllSkills(UUID token) {
        return employeeService.getAllSkills(token);
    }

    public ServerResponse getAllVacancies(UUID token) {
        return employerService.getAllVacancies(token);
    }

    public ServerResponse getAllRequirements(UUID token) {
        return employerService.getAllRequirements(token);
    }

    public ServerResponse getRequirementByIdExternal(UUID token, int id) {
        return employerService.getRequirementByIdExternal(token, id);
    }

    public ServerResponse getVacancyByIdExternal(UUID token, int id) {
        return employerService.getVacancyByIdExternal(token, id);
    }

    public void clear() {
        debugService.clear();
    }
}
