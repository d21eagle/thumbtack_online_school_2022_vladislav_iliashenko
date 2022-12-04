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
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PASSWORD_LENGTH = 8;
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
            if (user == null) {
                throw new ServerException(ServerErrorCode.INVALID_TOKEN);
            }
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getEmployeeDto((Employee) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployeeById(int id) {
        try {
            User user = employeeDao.getUserById(id);
            if (user == null) {
                throw new ServerException(ServerErrorCode.INVALID_ID);
            }
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getEmployeeDto((Employee) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addSkill(UUID token, String requestJson) {
        try {
            AddSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, AddSkillDtoRequest.class);
            validateRequest(skillDtoRequest);
            Skill skill = EmployeeMapper.INSTANCE.skillToSkillDto(skillDtoRequest);

            ServerResponse employeeResponse = getEmployeeByToken(token);
            GetEmployeeDtoResponse employee = GSON.fromJson(employeeResponse.getResponseData(), GetEmployeeDtoResponse.class);

            EmployeeMapper.INSTANCE.getEmployee(employee).getSkills().add(skill);
            int id = employeeDao.addSkill(skill);
            AddSkillDtoResponse addSkillDtoResponse = new AddSkillDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addSkillDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteSkillById(String requestJson) {
        try {
            DeleteSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteSkillDtoRequest.class);
            int id = skillDtoRequest.getId();
            employeeDao.deleteSkillById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getSkillById(int id) {
        try {
            Skill skill = employeeDao.getSkillById(id);
            if (skill == null) {
                throw new ServerException(ServerErrorCode.INVALID_ID);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getSkill(skill)));
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
        if (request.getLogin().length() < MIN_LOGIN_LENGTH)
            throw new ServerException(ServerErrorCode.SHORT_LOGIN);
        if (request.getPassword().length() < MIN_PASSWORD_LENGTH)
            throw new ServerException(ServerErrorCode.SHORT_PASSWORD);
    }

    private void validateRequest(AddSkillDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getSkillName()))
            throw new ServerException(ServerErrorCode.EMPTY_SKILL_NAME);
        if (request.getProfLevel() <= 0)
            throw new ServerException(ServerErrorCode.INVALID_PROF_LEVEL);
    }
}
