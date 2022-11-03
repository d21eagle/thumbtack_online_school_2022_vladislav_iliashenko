package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.dao.*;


public class EmployeeService {

    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = new Gson().fromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = new Employee(
                    registerDtoRequest.getEmail(),
                    registerDtoRequest.getFirstName(),
                    registerDtoRequest.getMiddleName(),
                    registerDtoRequest.getLastName(),
                    registerDtoRequest.getLogin(),
                    registerDtoRequest.getPassword(),
                    registerDtoRequest.getSkills());
        } catch (ValidateException e) {
            //
        }
        return new ServerResponse(200, "");
    }
    private void validateRequest(RegisterEmployeeDtoRequest request) throws ValidateException {
        if (Strings.isNullOrEmpty(request.getFirstName()))
            throw new ValidateException(ValidateErrorCode.EMPTY_FIRST_NAME);
        if (Strings.isNullOrEmpty(request.getMiddleName()))
            throw new ValidateException(ValidateErrorCode.EMPTY_MIDDLE_NAME);
        if (Strings.isNullOrEmpty(request.getLastName()))
            throw new ValidateException(ValidateErrorCode.EMPTY_LAST_NAME);
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ValidateException(ValidateErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ValidateException(ValidateErrorCode.EMPTY_PASSWORD);
        if (request.getLogin().length() < MIN_LOGIN)
            throw new ValidateException(ValidateErrorCode.SHORT_LOGIN);
        if (request.getPassword().length() < MIN_PASSWORD)
            throw new ValidateException(ValidateErrorCode.SHORT_PASSWORD);
    }
}
