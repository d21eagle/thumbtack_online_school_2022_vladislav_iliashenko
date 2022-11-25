package net.thumbtack.school.hiring.server;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import java.util.UUID;

public class Server {

    private final EmployeeService employeeService = new EmployeeService();
    private final EmployerService employerService = new EmployerService();

    public ServerResponse registerEmployee (String requestJson) throws JsonSyntaxException {
        return employeeService.registerEmployee(requestJson);
    }
    public ServerResponse registerEmployer (String requestJson) throws JsonSyntaxException {
        return employerService.registerEmployee(requestJson);
    }

    public ServerResponse loginEmployee(String requestJson) {
        return employeeService.loginEmployee(requestJson);
    }

    public ServerResponse loginEmployer(String requestJson) {
        return employerService.loginEmployer(requestJson);
    }

    public ServerResponse logoutEmployee(String requestJson) {
        return employeeService.logoutEmployee(requestJson);
    }

    public ServerResponse logoutEmployer(String requestJson) {
        return employerService.logoutEmployer(requestJson);
    }

    public ServerResponse getEmployeeByToken(UUID token) {
        return employeeService.getEmployeeByToken(token);
    }

    public ServerResponse getEmployerByToken(UUID token) {
        return employerService.getEmployerByToken(token);
    }

    public void clear() {
        employeeService.clear();
    }
}
