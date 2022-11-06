package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;

import java.util.Map;
import java.util.UUID;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.dao.*;
import org.mapstruct.Mapper;


public class EmployeeService {

    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;

    // REVU private
    // и в конец класса все private методы
    public static <T> T getClassFromJson(String requestJsonString, Class<T> tempClass) throws ServerException {
        try {
            if (Strings.isNullOrEmpty(requestJsonString)) {
                // REVU нет, выбрасывайте сразу ServerException
                // Не Ваше дело выбрасывать JsonSyntaxException, его должен выбрасывать только Gson
                throw new JsonSyntaxException("");
            }
            return GSON.fromJson(requestJsonString, tempClass);
        }
        catch (JsonSyntaxException ex) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(registerDtoRequest);
            //insert employee to database
            EmptyResponse emptyResponse = new EmptyResponse();
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(emptyResponse));
        } catch (ServerException e) {
            ErrorResponse errorResponse = new ErrorResponse(e);
            return new ServerResponse(ERROR_CODE, GSON.toJson(errorResponse));
        }
    }
    private void validateRequest(RegisterEmployeeDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLastName()))
            throw new ServerException(ServerErrorCode.EMPTY_LAST_NAME);
        if (Strings.isNullOrEmpty(request.getFirstName()))
            throw new ServerException(ServerErrorCode.EMPTY_FIRST_NAME);
        if (Strings.isNullOrEmpty(request.getMiddleName()))
            throw new ServerException(ServerErrorCode.EMPTY_MIDDLE_NAME);
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
        if (request.getLogin().length() < MIN_LOGIN)
            throw new ServerException(ServerErrorCode.SHORT_LOGIN);
        if (request.getPassword().length() < MIN_PASSWORD)
            throw new ServerException(ServerErrorCode.SHORT_PASSWORD);
    }
}
