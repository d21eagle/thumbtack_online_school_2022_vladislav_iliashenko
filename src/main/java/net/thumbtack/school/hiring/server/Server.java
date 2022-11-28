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

    public ServerResponse getEmployeeByToken(UUID token) {
        return employeeService.getEmployeeByToken(token);
    }

    public ServerResponse getEmployerByToken(UUID token) {
        return employerService.getEmployerByToken(token);
    }

    public void clear() {
        debugService.clear();
    }
}
