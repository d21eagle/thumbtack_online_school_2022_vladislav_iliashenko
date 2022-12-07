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
            int userId = employeeDao.insert(employee);
            RegisterEmployeeDtoResponse registerEmployeeDtoResponse = new RegisterEmployeeDtoResponse(userId);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(registerEmployeeDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getCurrentEmployee(UUID token) {
        try {
            Employee employee = getEmployeeByToken(token);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getEmployeeDto(employee)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addSkill(UUID token, String requestJson) {
        try {
            Employee employee = getEmployeeByToken(token);
            AddSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, AddSkillDtoRequest.class);
            validateRequest(skillDtoRequest);
            Skill skill = EmployeeMapper.INSTANCE.skillToSkillDto(skillDtoRequest);

            employee.add(skill);

            int id = employeeDao.addSkill(skill);
            AddSkillDtoResponse addSkillDtoResponse = new AddSkillDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addSkillDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteSkill(UUID token, String requestJson) {
        try {
            Employee employee = getEmployeeByToken(token);
            DeleteSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteSkillDtoRequest.class);
            int skillId = skillDtoRequest.getSkillId();

            Skill skill = getSkillById(skillId);
            employee.delete(skill);

            employeeDao.deleteSkill(skillId);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getSkillByIdExternal(UUID token, int id) {
        try {
            Employee employee = getEmployeeByToken(token);
            Skill skill = getSkillById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployeeMapper.INSTANCE.getSkillDto(skill)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getAllSkills(UUID token) {
        try {
            Employee employee = getEmployeeByToken(token);
            List<Skill> skills = employeeDao.getAllSkills();
            if (skills.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_SKILLS_ERROR);
            }

            List<GetSkillDtoResponse> allSkillsResponse = new ArrayList<>();
            for (Skill item: skills) {
                allSkillsResponse.add(EmployeeMapper.INSTANCE.getSkillDto(item));
            }

            GetAllSkillsDtoResponse allSkillsDtoResponse = new GetAllSkillsDtoResponse();
            allSkillsDtoResponse.setSkills(allSkillsResponse);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(allSkillsDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Employee getEmployeeByToken(UUID token) throws ServerException {
        if (token == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }
        User user = employeeDao.getUserByToken(token);
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_EXIST);
        }
        if (!(user instanceof Employee)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        return (Employee) user;
    }

    private Skill getSkillById(int id) throws ServerException {
        Skill skill = employeeDao.getSkillById(id);
        if (skill == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        return skill;
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
