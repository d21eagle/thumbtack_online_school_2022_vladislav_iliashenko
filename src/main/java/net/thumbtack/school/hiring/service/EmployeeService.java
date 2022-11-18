package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.mapper.EmployeeMapper;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.server.ServerUtils;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;

public class EmployeeService {

    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
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

    public ServerResponse loginEmployee(String requestJson) throws ServerException {
        try {
            LoginEmployeeDtoRequest loginEmployeeDtoRequest = ServerUtils.getClassFromJson(requestJson, LoginEmployeeDtoRequest.class);
            validateRequest(loginEmployeeDtoRequest);
            User user = employeeDao.getUserByLogin(loginEmployeeDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginEmployeeDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = employeeDao.loginUser(user);
            LoginEmployeeDtoResponse loginUserDtoResponse = new LoginEmployeeDtoResponse(uuid);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            ErrorResponse errorDtoResponse = new ErrorResponse(e);
            return new ServerResponse(ERROR_CODE, GSON.toJson(errorDtoResponse));
        }
    }

    private void validateRequest(LoginEmployeeDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }

    public ServerResponse logoutEmployee(String requestJson) throws ServerException {
        try {
            LogoutEmployeeDtoRequest logoutEmployeeDtoRequest = ServerUtils.getClassFromJson(requestJson, LogoutEmployeeDtoRequest.class);
            employeeDao.logoutUser(logoutEmployeeDtoRequest);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        }
        catch (ServerException e) {
            ErrorResponse errorDtoResponse = new ErrorResponse(e);
            return new ServerResponse(ERROR_CODE, GSON.toJson(errorDtoResponse));
        }
    }

    public UUID getToken(String login) {
        return Database.getInstance().getToken(login);
    }

    public Employee getEmployeeByToken(UUID token) {
       return (Employee) Database.getTokens().get(token);
    }
}
