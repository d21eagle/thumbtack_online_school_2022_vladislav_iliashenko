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
import java.util.*;

public class EmployeeService {

    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(registerDtoRequest);
            employeeDao.insert(employee);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(""));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse loginEmployee(String requestJson) {
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
            return new ServerResponse(e);
        }
    }

    public ServerResponse logoutEmployee(String requestJson) {
        try {
            LogoutEmployeeDtoRequest logoutEmployeeDtoRequest = ServerUtils.getClassFromJson(requestJson, LogoutEmployeeDtoRequest.class);
            employeeDao.logoutUser(logoutEmployeeDtoRequest);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        }
        catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployeeByToken(UUID token) {
        try {
            User user = employeeDao.getUserByToken(token);
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    new GetEmployeeByTokenDtoResponse((Employee) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }
    public void clear() {
        employeeDao.clear();
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

    private void validateRequest(LoginEmployeeDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }
}
