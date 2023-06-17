package net.thumbtack.school.hiring.service;

import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.collection.RamEmployerDao;
import net.thumbtack.school.hiring.dao.sql.SqlEmployerDao;
import net.thumbtack.school.hiring.daoimpl.collections.RamEmployerDaoImpl;
import net.thumbtack.school.hiring.daoimpl.sql.SqlEmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.mapper.EmployerMapper;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.utils.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

public class EmployerService extends UserService {
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final SqlEmployerDao sqlEmployerDao = new SqlEmployerDaoImpl();
    private final RamEmployerDao ramEmployerDao = new RamEmployerDaoImpl();
    private final Settings settings = Settings.getInstance();

    public Response registerEmployer(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployerDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employer employer = EmployerMapper.INSTANCE.employerToEmployerDto(registerDtoRequest);

            int userId;
            if (settings.getDatabaseType().equals("SQL")) {
                userId = sqlEmployerDao.insert(employer);
            }
            else {
                userId = ramEmployerDao.insert(employer);
            }

            RegisterEmployerDtoResponse registerEmployerDtoResponse = new RegisterEmployerDtoResponse(userId);
            return Response.ok(registerEmployerDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getCurrentEmployer(UUID token) {
        try {
            Employer employer = getEmployerByToken(token);
            return Response.ok(EmployerMapper.INSTANCE.getEmployerDto(employer), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response addVacancy(UUID token, String requestJson) {
        try {
            Employer employer = getEmployerByToken(token);
            AddVacancyDtoRequest vacancyDtoRequest = ServerUtils.getClassFromJson(requestJson, AddVacancyDtoRequest.class);
            validateRequest(vacancyDtoRequest);
            Vacancy vacancy = EmployerMapper.INSTANCE.vacancyToVacancyDto(vacancyDtoRequest);

            employer.add(vacancy);

            int id;
            if (settings.getDatabaseType().equals("SQL")) {
                id = sqlEmployerDao.addVacancy(vacancy, employer);
            }
            else {
                id = ramEmployerDao.addVacancy(vacancy);
            }

            AddVacancyDtoResponse addVacancyDtoResponse = new AddVacancyDtoResponse(id);
            return Response.ok(addVacancyDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response addVacancyRequirement(UUID token, String requestJson) {
        try {
            Employer employer = getEmployerByToken(token);
            AddRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(requestJson, AddRequirementDtoRequest.class);
            validateRequest(requirementDtoRequest);
            Requirement requirement = EmployerMapper.INSTANCE.requirementToRequirementDto(requirementDtoRequest);

            Vacancy vacancy = new Vacancy(requirementDtoRequest.getVacancyId());
            requirement.setVacancy(vacancy);
            vacancy.add(requirement);

            int id;
            if (settings.getDatabaseType().equals("SQL")) {
                id = sqlEmployerDao.addVacancyRequirement(requirement, vacancy);
            }
            else {
                id = ramEmployerDao.addVacancyRequirement(requirement);
            }

            AddRequirementDtoResponse addRequirementDtoResponse = new AddRequirementDtoResponse(id);
            return Response.ok(addRequirementDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response deleteVacancy(UUID token, int vacancyId) {
        try {
            Employer employer = getEmployerByToken(token);

            Vacancy vacancy = getVacancyById(vacancyId);
            vacancy.deleteAll();
            employer.delete(vacancy);

            if (settings.getDatabaseType().equals("SQL")) {
                sqlEmployerDao.deleteVacancy(vacancyId);
            }
            else {
                ramEmployerDao.deleteVacancy(vacancyId);
            }

            return Response.ok(new EmptyResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response deleteVacancyRequirement(UUID token, int requirementId) {
        try {
            getEmployerByToken(token);
            Requirement requirement = getRequirementById(requirementId);
            Vacancy vacancy;
            if (settings.getDatabaseType().equals("SQL")) {
                vacancy = new Vacancy(sqlEmployerDao.getIdVacancyByRequirement(requirement));
                vacancy.delete(requirement);
                sqlEmployerDao.deleteVacancyRequirement(requirementId);
            }
            else {
                vacancy = requirement.getVacancy();
                vacancy.delete(requirement);
                ramEmployerDao.deleteVacancyRequirement(requirementId);
            }

            return Response.ok(new EmptyResponse(), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getRequirementByIdExternal(UUID token, int id) {
        try {
            getEmployerByToken(token);
            Requirement employeeRequirement = getRequirementById(id);
            return Response.ok(EmployerMapper.INSTANCE.getVacancyRequirementDto(employeeRequirement), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getVacancyByIdExternal(UUID token, int id) {
        try {
            getEmployerByToken(token);
            Vacancy vacancy = getVacancyById(id);
            return Response.ok(EmployerMapper.INSTANCE.getVacancyDto(vacancy), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getAllVacancies(UUID token) {
        try {
            getEmployerByToken(token);

            List<Vacancy> vacancies;
            if (settings.getDatabaseType().equals("SQL")) {
                vacancies = sqlEmployerDao.getAllVacancies();
            }
            else {
                vacancies = ramEmployerDao.getAllVacancies();
            }

            if (vacancies.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_VACANCIES_ERROR);
            }

            List<GetVacancyDtoResponse> allVacanciesResponse = new ArrayList<>();
            for (Vacancy item: vacancies) {
                allVacanciesResponse.add(EmployerMapper.INSTANCE.getVacancyDto(item));
            }

            GetAllVacanciesDtoResponse allVacanciesDtoResponse = new GetAllVacanciesDtoResponse();
            allVacanciesDtoResponse.setVacancies(allVacanciesResponse);
            return Response.ok(allVacanciesDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getAllRequirements(UUID token) {
        try {
            getEmployerByToken(token);
            List<Requirement> requirements;
            if (settings.getDatabaseType().equals("SQL")) {
                requirements = sqlEmployerDao.getAllRequirements();
            }
            else {
                requirements = ramEmployerDao.getAllRequirements();
            }

            if (requirements.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_REQUIREMENTS_ERROR);
            }

            List<GetRequirementDtoResponse> allRequirementsResponse = new ArrayList<>();
            for (Requirement item: requirements) {
                allRequirementsResponse.add(EmployerMapper.INSTANCE.getVacancyRequirementDto(item));
            }

            GetAllRequirementsDtoResponse allRequirementsDtoResponse = new GetAllRequirementsDtoResponse();
            allRequirementsDtoResponse.setRequirementsList(allRequirementsResponse);
            return Response.ok(allRequirementsDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response getEmployeesByRequirements(UUID token, String requestJson) {
        try {
            getEmployerByToken(token);
            RequirementListDtoRequest requirementRequest = ServerUtils.getClassFromJson(requestJson, RequirementListDtoRequest.class);
            List<RequirementDtoRequest> requirements = requirementRequest.getRequirementList();
            List<Requirement> requirementList = new ArrayList<>();
            for (RequirementDtoRequest requirement : requirements) {
                requirementList.add(new Requirement(requirement.getRequirementName(), requirement.getProfLevel(), requirement.isNecessary()));
            }
            Set<Employee> employeeSet = ramEmployerDao.getEmployeesByRequirements(requirementList);
            Set<EmployeeDtoResponse> shortlist = new HashSet<>();
            for (Employee employee : employeeSet) {
                shortlist.add(new EmployeeDtoResponse(
                        employee.getEmail(),
                        employee.getLogin(),
                        employee.getLastName(),
                        employee.getMiddleName(),
                        employee.getFirstName()
                ));
            }
            return Response.ok(new EmployeeSetDtoResponse(shortlist), MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    private Employer getEmployerByToken(UUID token) throws ServerException {
        if (token == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }

        User user;
        if (settings.getDatabaseType().equals("SQL")) {
            user = sqlEmployerDao.getEmployerByToken(String.valueOf(token));
        }
        else {
            user = ramEmployerDao.getUserByToken(token);
        }

        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_EXIST);
        }
        if (!(user instanceof Employer)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        return (Employer) user;
    }

    private Vacancy getVacancyById(int id) throws ServerException {
        Vacancy vacancy;
        if (settings.getDatabaseType().equals("SQL")) {
            vacancy = sqlEmployerDao.getVacancyById(id);
        }
        else {
            vacancy = ramEmployerDao.getVacancyById(id);
        }

        if (vacancy == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        return vacancy;
    }

    private Requirement getRequirementById(int id) throws ServerException {
        Requirement requirement;
        if (settings.getDatabaseType().equals("SQL")) {
            requirement = sqlEmployerDao.getRequirementById(id);
        }
        else {
            requirement = ramEmployerDao.getRequirementById(id);
        }

        if (requirement == null) {
            throw new ServerException(ServerErrorCode.INVALID_ID);
        }
        return requirement;
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
    }

    private void validateRequest(AddRequirementDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getRequirementName()))
            throw new ServerException(ServerErrorCode.EMPTY_REQUIREMENT_NAME);
        if (request.getProfLevel() <= 0)
            throw new ServerException(ServerErrorCode.INVALID_PROF_LEVEL);
    }
}
