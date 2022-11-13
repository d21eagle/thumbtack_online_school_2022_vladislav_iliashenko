package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TestEmployerService {

    @Test
    public void TestRegisterEmployer_Success() {
        Server server = new Server();
        final int SUCCESS_CODE = 200;
        final Gson GSON = new Gson();

        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "ул.Ленина д.19/2",
                "hiretool.it@gmail.com",
                "Петров",
                "Пётр",
                "Петрович",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse = server.registerEmployer(GSON.toJson(requestJson));
        assertEquals(actualResponse.getResponseCode(), SUCCESS_CODE);
    }

    @Test
    public void TestRegisterEmployer_Failed() {
        Server server = new Server();
        final int ERROR_CODE = 400;
        final Gson GSON = new Gson();
        JsonObject error = new JsonObject();

        RegisterEmployerDtoRequest requestJson = new RegisterEmployerDtoRequest(
                "HireTool",
                "",
                "hiretool.it@gmail.com",
                "Петров",
                "Пётр",
                "Петрович",
                "hiretool_HRdep",
                "423657801"
        );

        ServerResponse actualResponse = server.registerEmployer(GSON.toJson(requestJson));
        error.addProperty("errorResp", "Пустой адрес компании!");
        assertEquals(actualResponse.getResponseCode(), ERROR_CODE);
        assertEquals(actualResponse.getResponseData(), error.toString());
    }
}
