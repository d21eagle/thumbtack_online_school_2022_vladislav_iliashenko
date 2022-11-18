package net.thumbtack.school.hiring.server;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import net.thumbtack.school.hiring.model.*;
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

    public ServerResponse loginEmployee(String requestJson) throws ServerException {
        return employeeService.loginEmployee(requestJson);
    }

    public ServerResponse logoutEmployee(String requestJson) throws ServerException {
        return employeeService.logoutEmployee(requestJson);
    }

    public UUID getToken(String login) {
        return employeeService.getToken(login);
    }

    public Employee getEmployeeByToken(UUID token) {
        return employeeService.getEmployeeByToken(token);
    }
}
