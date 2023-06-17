package net.thumbtack.school.hiring.service;

import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.collection.RamEmployeeDao;
import net.thumbtack.school.hiring.daoimpl.collections.RamEmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.sql.SqlEmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.mapper.EmployeeMapper;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dao.sql.SqlEmployeeDao;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.utils.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

public class EmployeeService extends UserService {
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final SqlEmployeeDao sqlEmployeeDao = new SqlEmployeeDaoImpl();
    private final RamEmployeeDao ramEmployeeDao = new RamEmployeeDaoImpl();
    private static final Settings settings = Settings.getInstance();

    public Response registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployeeDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployeeDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employee employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(registerDtoRequest);

            int userId;
            if (settings.getDatabaseType().equals("SQL")) {
                userId = sqlEmployeeDao.insert(employee);
            }
            else {
                userId = ramEmployeeDao.insert(employee);
            }

            RegisterEmployeeDtoResponse registerEmployeeDtoResponse = new RegisterEmployeeDtoResponse(userId);
            return Response.ok(registerEmployeeDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getCurrentEmployee(UUID token) {
        try {
            Employee employee = getEmployeeByToken(token);
            return Response.ok(EmployeeMapper.INSTANCE.getEmployeeDto(employee), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response addSkill(UUID token, String requestJson) {
        try {
            Employee employee = getEmployeeByToken(token);
            AddSkillDtoRequest skillDtoRequest = ServerUtils.getClassFromJson(requestJson, AddSkillDtoRequest.class);
            validateRequest(skillDtoRequest);
            Skill skill = EmployeeMapper.INSTANCE.skillToSkillDto(skillDtoRequest);

            employee.add(skill);

            int id;
            if (settings.getDatabaseType().equals("SQL")) {
                id = sqlEmployeeDao.addSkill(skill, employee);
            }
            else {
                id = ramEmployeeDao.addSkill(skill, employee);
            }

            AddSkillDtoResponse addSkillDtoResponse = new AddSkillDtoResponse(id);
            return Response.ok(addSkillDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response deleteSkill(UUID token, int skillId) {
        try {
            Employee employee = getEmployeeByToken(token);
            Skill skill = getSkillById(skillId);
            employee.delete(skill);

            if (settings.getDatabaseType().equals("SQL")) {
                sqlEmployeeDao.deleteSkill(skillId);
            }
            else {
                ramEmployeeDao.deleteSkill(skillId);
            }

            return Response.ok(new EmptyResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getSkillByIdExternal(UUID token, int skillId) {
        try {
            getEmployeeByToken(token);
            Skill skill = getSkillById(skillId);
            return Response.ok(EmployeeMapper.INSTANCE.getSkillDto(skill), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getAllSkills(UUID token) {
        try {
            getEmployeeByToken(token);

            List<Skill> skills;
            if (settings.getDatabaseType().equals("SQL")) {
                skills = sqlEmployeeDao.getAllSkills();
            }
            else {
                skills = ramEmployeeDao.getAllSkills();
            }

            if (skills.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_SKILLS_ERROR);
            }

            List<GetSkillDtoResponse> allSkillsResponse = new ArrayList<>();
            for (Skill item: skills) {
                allSkillsResponse.add(EmployeeMapper.INSTANCE.getSkillDto(item));
            }
            GetAllSkillsDtoResponse allSkillsDtoResponse = new GetAllSkillsDtoResponse();
            allSkillsDtoResponse.setSkills(allSkillsResponse);
            return Response.ok(allSkillsDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    private Employee getEmployeeByToken(UUID token) throws ServerException {
        if (token == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }

        User user;
        if (settings.getDatabaseType().equals("SQL")) {
            user = sqlEmployeeDao.getEmployeeByToken(String.valueOf(token));
        }
        else {
            user = ramEmployeeDao.getUserByToken(token);
        }

        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_EXIST);
        }
        if (!(user instanceof Employee)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        return (Employee) user;
    }

    private Skill getSkillById(int id) throws ServerException {
        Skill skill;
        if (settings.getDatabaseType().equals("SQL")) {
            skill = sqlEmployeeDao.getSkillById(id);
        }
        else {
            skill = ramEmployeeDao.getSkillById(id);
        }

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
