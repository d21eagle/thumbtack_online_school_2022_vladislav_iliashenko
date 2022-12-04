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

    // REVU упорядочите
    // сначала действия с Employee (регистрация, получение по токену и id и т.д.
    // потом скиллы и т.д.
    // машине это все равно, а читать человеку легче
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

    public ServerResponse addSkill(UUID token, String requestJson) {
        try {
            AddSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, AddSkillDtoRequest.class);
            validateRequest(skillDtoRequest);
            Skill skill = EmployeeMapper.INSTANCE.skillToSkillDto(skillDtoRequest);
            // REVU следующие 4 строки понадобятся во всех методах, где передается токен
            // сделайте тут
            // private Employee getEmployeeByToken
            // он должен либо вернуть Employee, либо выбросить исключение
            // и используйте его везде
            // Employer - аналогично
            User user = employeeDao.getUserByToken(token);
            // проверка на null
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            employeeDao.addSkill((Employee) user, skill);
            // REVU а вот тут в ответе я бы вернул id, назначенный этому скиллу
            // тогда в deleteSkill будет достаточно этот id передать
            // и внутри его getSkillById
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    // REVU скорее всего не нужен
    // есть же deleteSkillById
    // да и странно как-то - deleteSkill использует AddSkillDtoRequest
    public ServerResponse deleteSkill(UUID token, String requestJson) {
        try {
            AddSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, AddSkillDtoRequest.class);
            validateRequest(skillDtoRequest);
            Skill skill = EmployeeMapper.INSTANCE.skillToSkillDto(skillDtoRequest);
            User user = employeeDao.getUserByToken(token);
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            employeeDao.deleteSkill((Employee) user, skill);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
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

    public ServerResponse getEmployeeByToken(UUID token) {
        try {
            // REVU а если нет такого токена в БД ?
            // проверка на null
            User user = employeeDao.getUserByToken(token);
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getEmployee((Employee) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getEmployeeById(int id) {
        try {
            // REVU аналогично
            User user = employeeDao.getUserById(id);
            if (!(user instanceof Employee)) {
                throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
            }
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getEmployee((Employee) user)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getSkillById(int id) {
        // REVU а если нет такого id ?
        // тоже проверка на null
        Skill skill = employeeDao.getSkillById(id);
        return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                EmployeeMapper.INSTANCE.getSkill(skill)));
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
