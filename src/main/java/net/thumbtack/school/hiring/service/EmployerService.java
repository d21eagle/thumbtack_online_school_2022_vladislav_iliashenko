package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.User;
import java.util.UUID;

public class EmployerService extends UserService {

    public EmployerService(String stringJson) {
        super(stringJson);
    }

    private ServerResponse validate() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new Gson().fromJson(getStringJson(), RegisterEmployerDtoRequest.class);
        return new ServerResponse(200, "");
    }
}
