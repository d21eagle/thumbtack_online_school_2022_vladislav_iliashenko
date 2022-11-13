package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.response.ErrorResponse;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.server.Server;
import net.thumbtack.school.hiring.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.Gson;

public class TestEmployerService {
    Server server = new Server();
    final Gson GSON = new Gson();
    final int SUCCESS_CODE = 200;
    final int ERROR_CODE = 400;

    @Test
    public void TestRegisterEmployer_Success() {
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
        assertEquals(actualResponse.getResponseCode(), ERROR_CODE);
        assertEquals(
                actualResponse.getResponseData(),
                GSON.toJson(new ErrorResponse(new ServerException(ServerErrorCode.EMPTY_COMPANY_ADDRESS))));
    }
}
