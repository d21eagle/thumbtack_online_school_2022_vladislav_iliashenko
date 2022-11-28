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

public class EmployeeService extends UserService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    // REVU MIN_LOGIN_LENGTH
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(registerDtoRequest);
            employeeDao.insert(employee);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
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
                    // REVU может, lombok ?
                    new GetEmployeeByTokenDtoResponse(
                            user.getEmail(),
                            user.getLogin(),
                            user.getPassword(),
                            user.getLastName(),
                            user.getMiddleName(),
                            user.getFirstName(),
                            ((Employee) user).getSkills()
                    )));
        } catch (ServerException e) {
            return new ServerResponse(e);
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
