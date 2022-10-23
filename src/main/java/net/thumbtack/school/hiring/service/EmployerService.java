package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.server.ServerResponse;

public class EmployerService extends UserService {

    public EmployerService(String stringJson) {
        super(stringJson);
    }

    private ServerResponse validate() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new Gson().fromJson(getStringJson(), RegisterEmployerDtoRequest.class);
        return new ServerResponse(200, "");
    }
}
