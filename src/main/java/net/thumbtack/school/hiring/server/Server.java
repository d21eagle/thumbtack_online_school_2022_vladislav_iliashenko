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

    public ServerResponse deleteSkill(String requestJson) {
        return employeeService.deleteSkill(requestJson);
    }

    public ServerResponse addVacancy(UUID token, String requestJson) {
        return employerService.addVacancy(token, requestJson);
    }

    public ServerResponse addEmployeeRequirement(String requestJson) {
        return employerService.addEmployeeRequirement(requestJson);
    }

    public ServerResponse deleteVacancy(String requestJson) {
        return employerService.deleteVacancy(requestJson);
    }

    public ServerResponse deleteEmployeeRequirementById(String requestJson) {
        return employerService.deleteEmployeeRequirement(requestJson);
    }

    public ServerResponse getCurrentEmployee(UUID token) {
        return employeeService.getCurrentEmployee(token);
    }

    public ServerResponse getCurrentEmployer(UUID token) {
        return employerService.getCurrentEmployer(token);
    }

    public ServerResponse getCurrentSkill(int id) {
        return employeeService.getCurrentSkill(id);
    }

    public ServerResponse getCurrentEmployeeRequirement(int id) {
        return employerService.getCurrentEmployeeRequirement(id);
    }

    public ServerResponse getCurrentVacancy(int id) {
        return employerService.getCurrentVacancy(id);
    }

    public void clear() {
        debugService.clear();
    }
}
