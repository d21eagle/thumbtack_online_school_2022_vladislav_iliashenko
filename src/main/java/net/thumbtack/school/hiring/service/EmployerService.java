package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.mapper.EmployeeMapper;
import net.thumbtack.school.hiring.mapper.EmployerMapper;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.server.ServerUtils;
import java.util.*;

public class EmployerService extends UserService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final EmployerDao employerDao = new EmployerDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployerDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employer employer = EmployerMapper.INSTANCE.employerToEmployerDto(registerDtoRequest);
            employerDao.insert(employer);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
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
                    EmployerMapper.INSTANCE.getEmployer((Employer) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployerById(int id) {
        try {
            User user = employerDao.getUserById(id);
            if (!(user instanceof Employer)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getEmployer((Employer) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployeeRequirementById(int id) {
        EmployeeRequirement employeeRequirement = employerDao.getRequirementById(id);
        return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                EmployerMapper.INSTANCE.getEmployeeRequirement(employeeRequirement)));
    }

    public ServerResponse getVacancyById(int id) {
        Vacancy vacancy = employerDao.getVacancyById(id);
        return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                EmployerMapper.INSTANCE.getVacancy(vacancy)));
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
        if (request.getLogin().length() < MIN_LOGIN_LENGTH)
            throw new ServerException(ServerErrorCode.SHORT_LOGIN);
        if (request.getPassword().length() < MIN_PASSWORD_LENGTH)
            throw new ServerException(ServerErrorCode.SHORT_PASSWORD);
    }
}
