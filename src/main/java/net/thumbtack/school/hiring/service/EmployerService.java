package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.mapper.EmployerMapper;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.server.ServerUtils;
import java.util.*;

public class EmployerService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    private final EmployerDao employerDao = new EmployerDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployerDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employer employer = EmployerMapper.INSTANCE.employerToEmployerDto(registerDtoRequest);
            employerDao.insert(employer);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(""));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    // REVU а чем это отличается от loginEmployee ? Вроде как ничем
    // можно сделать UserService и отправить его туда
    public ServerResponse loginEmployer(String requestJson) {
        try {
            // REVU а тут LoginUserDtoRequest
            LoginEmployerDtoRequest loginEmployerDtoRequest = ServerUtils.getClassFromJson(requestJson, LoginEmployerDtoRequest.class);
            validateRequest(loginEmployerDtoRequest);
            User user = employerDao.getUserByLogin(loginEmployerDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginEmployerDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = employerDao.loginUser(user);
            // LoginUserDtoResponse
            LoginEmployerDtoResponse loginUserDtoResponse = new LoginEmployerDtoResponse(uuid);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    // REVU и этот тоже
    public ServerResponse logoutEmployer(String requestJson) {
        try {
            LogoutEmployerDtoRequest logoutEmployerDtoRequest = ServerUtils.getClassFromJson(requestJson, LogoutEmployerDtoRequest.class);
            employerDao.logoutUser(logoutEmployerDtoRequest);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        }
        catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployerByToken(UUID token) {
        try {
            User user = employerDao.getUserByToken(token);
            if (!(user instanceof Employer)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    new GetEmployerByTokenDtoResponse((Employer) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }
    public void clear() {
        employerDao.clear();
    }

    private void validateRequest(RegisterEmployerDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getCompanyName()))
            throw new ServerException(ServerErrorCode.EMPTY_COMPANY_NAME);
        if (Strings.isNullOrEmpty(request.getCompanyAddress()))
            throw new ServerException(ServerErrorCode.EMPTY_COMPANY_ADDRESS);
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

    private void validateRequest(LoginEmployerDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }
}
