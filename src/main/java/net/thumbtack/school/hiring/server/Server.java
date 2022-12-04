package net.thumbtack.school.hiring.server;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.service.*;
import java.util.UUID;

public class Server {

    private final EmployeeService employeeService = new EmployeeService();
    private final EmployerService employerService = new EmployerService();
    private final UserService userService = new UserService();
    private final DebugService debugService = new DebugService();

    public ServerResponse registerEmployee (String requestJson) throws JsonSyntaxException {
        return employeeService.registerEmployee(requestJson);
    }
    public ServerResponse registerEmployer (String requestJson) throws JsonSyntaxException {
        return employerService.registerEmployee(requestJson);
    }

    public ServerResponse loginUser(String requestJson) {
        return userService.loginUser(requestJson);
    }

    public ServerResponse logoutUser(String requestJson) {
        return userService.logoutUser(requestJson);
    }

    public ServerResponse addSkill(UUID token, String requestJson) {
        return employeeService.addSkill(token, requestJson);
    }

    public ServerResponse deleteSkillById(String requestJson) {
        return employeeService.deleteSkillById(requestJson);
    }

    public ServerResponse addVacancy(UUID token, String requestJson) {
        return employerService.addVacancy(token, requestJson);
    }

    public ServerResponse addEmployeeRequirement(String requestJson) {
        return employerService.addEmployeeRequirement(requestJson);
    }

    public ServerResponse deleteVacancyById(String requestJson) {
        return employerService.deleteVacancyById(requestJson);
    }

    public ServerResponse deleteEmployeeRequirementById(String requestJson) {
        return employerService.deleteEmployeeRequirementById(requestJson);
    }

    public ServerResponse getEmployeeByToken(UUID token) {
        return employeeService.getEmployeeByToken(token);
    }

    public ServerResponse getEmployerByToken(UUID token) {
        return employerService.getEmployerByToken(token);
    }

    public ServerResponse getEmployeeById(int id) {
        return employeeService.getEmployeeById(id);
    }

    public ServerResponse getEmployerById(int id) {
        return employerService.getEmployerById(id);
    }

    public ServerResponse getSkillById(int id) {
        return employeeService.getSkillById(id);
    }

    public ServerResponse getEmployeeRequirementById(int id) {
        return employerService.getEmployeeRequirementById(id);
    }

    public ServerResponse getVacancyById(int id) {
        return employerService.getVacancyById(id);
    }

    public void clear() {
        debugService.clear();
    }
}
