package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.EmployerDao;
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

    public ServerResponse getCurrentEmployer(UUID token) {
        try {
            Employer employer = getEmployerByToken(token);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getEmployerDto(employer)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addVacancy(UUID token, String requestJson) {
        try {
            AddVacancyDtoRequest vacancyDtoRequest = ServerUtils.getClassFromJson(requestJson, AddVacancyDtoRequest.class);
            validateRequest(vacancyDtoRequest);
            Vacancy vacancy = EmployerMapper.INSTANCE.vacancyToVacancyDto(vacancyDtoRequest);

            Employer employer = getEmployerByToken(token);
            employer.getVacancies().add(vacancy);

            int id = employerDao.addVacancy(vacancy);
            AddVacancyDtoResponse addVacancyDtoResponse = new AddVacancyDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addVacancyDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addEmployeeRequirement(String requestJson) {
        try {
            AddEmployeeRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(requestJson, AddEmployeeRequirementDtoRequest.class);
            validateRequest(requirementDtoRequest);
            EmployeeRequirement requirement = EmployerMapper.INSTANCE.requirementToRequirementDto(requirementDtoRequest);

            Vacancy vacancy = getVacancyById(requirementDtoRequest.getId());
            vacancy.getRequirementsList().add(requirement);

            int id = employerDao.addEmployeeRequirement(requirement);
            AddEmployeeRequirementDtoResponse addEmployeeRequirementDtoResponse = new AddEmployeeRequirementDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addEmployeeRequirementDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteVacancy(String requestJson) {
        try {
            DeleteVacancyDtoRequest vacancyDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteVacancyDtoRequest.class);
            int id = vacancyDtoRequest.getId();
            employerDao.deleteVacancy(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteEmployeeRequirement(String requestJson) {
        try {
            DeleteEmployeeRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteEmployeeRequirementDtoRequest.class);
            int id = requirementDtoRequest.getId();
            employerDao.deleteEmployeeRequirement(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getCurrentEmployeeRequirement(int id) {
        try {
            EmployeeRequirement employeeRequirement = getEmployeeRequirementById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getEmployeeRequirement(employeeRequirement)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getCurrentVacancy(int id) {
        try {
            Vacancy vacancy = getVacancyById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getVacancy(vacancy)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Employer getEmployerByToken(UUID token) throws ServerException {
        User user = employerDao.getUserByToken(token);
        if (user == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }
        if (!(user instanceof Employer)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        return (Employer) user;
    }

    private Employer getEmployerById(int id) throws ServerException {
        User user = employerDao.getUserById(id);
        if (user == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        if (!(user instanceof Employer)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        return (Employer) user;
    }

    private Vacancy getVacancyById(int id) throws ServerException {
        Vacancy vacancy = employerDao.getVacancyById(id);
        if (vacancy == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        return vacancy;
    }

    private EmployeeRequirement getEmployeeRequirementById(int id) throws ServerException {
        EmployeeRequirement employeeRequirement = employerDao.getRequirementById(id);
        if (employeeRequirement == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        return employeeRequirement;
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

    private void validateRequest(AddVacancyDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getPosition()))
            throw new ServerException(ServerErrorCode.EMPTY_POSITION);
        if (request.getSalary() <= 0)
            throw new ServerException(ServerErrorCode.INVALID_SALARY);
        if (request.getEmployer() == null)
            throw new ServerException(ServerErrorCode.INVALID_EMPLOYER);
    }

    private void validateRequest(AddEmployeeRequirementDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getRequirementName()))
            throw new ServerException(ServerErrorCode.EMPTY_REQUIREMENT_NAME);
        if (request.getProfLevel() <= 0)
            throw new ServerException(ServerErrorCode.INVALID_PROF_LEVEL);
    }
}
