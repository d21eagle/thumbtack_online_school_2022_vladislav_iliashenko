package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.server.ServerResponse;

public class EmployeeService extends UserService {

    public EmployeeService(String stringJson) {
        super(stringJson);
    }
    private ServerResponse validate() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new Gson().fromJson(getStringJson(), RegisterEmployeeDtoRequest.class);
        return new ServerResponse(200, "");
    }
}
