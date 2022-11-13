package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.mapper.EmployeeMapper;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;

public class EmployeeService {

    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    // REVU не нужно static. Кто его знает, вдруг понадобится еще один экземпляр EmployeeDao
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(registerDtoRequest);
            employeeDao.insert(employee);
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

    // REVU этот метод у Вас в 2 классах
    // либо вынести его в ServerUtils и там static
    // либо сделать все сервисы наследниками от ServiceBase и его туда как protected
    private static <T> T getClassFromJson(String requestJsonString, Class<T> tempClass) throws ServerException {
        if (Strings.isNullOrEmpty(requestJsonString)) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
        return GSON.fromJson(requestJsonString, tempClass);
    }
}
