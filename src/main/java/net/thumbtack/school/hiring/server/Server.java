package net.thumbtack.school.hiring.server;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import net.thumbtack.school.hiring.server.ServerResponse;
import com.google.gson.Gson;
import java.util.UUID;

public class Server {

    private EmployeeService employeeService;
    private EmployerService employerService;
    private ServerResponse serverResponse;
    private UUID id;
}
