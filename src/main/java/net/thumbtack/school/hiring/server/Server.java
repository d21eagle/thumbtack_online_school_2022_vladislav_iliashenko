package net.thumbtack.school.hiring.server;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.exception.ServerException;

public class Server {

    private EmployeeService employeeService = new EmployeeService();
    private EmployerService employerService = new EmployerService();

    public ServerResponse registerEmployee (String requestJson) throws ServerException {
        return employeeService.registerEmployee(requestJson);
    }
}
