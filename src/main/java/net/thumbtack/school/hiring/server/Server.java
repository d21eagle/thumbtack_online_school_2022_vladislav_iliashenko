package net.thumbtack.school.hiring.server;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

public class Server {

    private final EmployeeService employeeService = new EmployeeService();
    private final EmployerService employerService = new EmployerService();

    public ServerResponse registerEmployee (String requestJson) throws JsonSyntaxException {
        return employeeService.registerEmployee(requestJson);
    }
    public ServerResponse registerEmployer (String requestJson) throws JsonSyntaxException {
        return employerService.registerEmployee(requestJson);
    }
}
