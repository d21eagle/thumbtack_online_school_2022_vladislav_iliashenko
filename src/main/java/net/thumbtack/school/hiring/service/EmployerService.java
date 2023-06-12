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
import net.thumbtack.school.hiring.utils.ServerUtils;
import java.util.*;

public class EmployerService extends UserService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final EmployerDao employerDao = new EmployerDaoImpl();

    public ServerResponse registerEmployer(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployerDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employer employer = EmployerMapper.INSTANCE.employerToEmployerDto(registerDtoRequest);
            int userId = employerDao.insert(employer);
            RegisterEmployerDtoResponse registerEmployerDtoResponse = new RegisterEmployerDtoResponse(userId);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(registerEmployerDtoResponse));
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
            Employer employer = getEmployerByToken(token);
            employer.setUserId(employerDao.getIdByEmployer(String.valueOf(token)));
            AddVacancyDtoRequest vacancyDtoRequest = ServerUtils.getClassFromJson(requestJson, AddVacancyDtoRequest.class);
            validateRequest(vacancyDtoRequest);
            Vacancy vacancy = EmployerMapper.INSTANCE.vacancyToVacancyDto(vacancyDtoRequest);

            employer.add(vacancy);

            int id = employerDao.addVacancy(vacancy, employer);
            AddVacancyDtoResponse addVacancyDtoResponse = new AddVacancyDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addVacancyDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addVacancyRequirement(UUID token, String requestJson) {
        try {
            Employer employer = getEmployerByToken(token);
            employer.setUserId(employerDao.getIdByEmployer(String.valueOf(token)));

            AddRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(requestJson, AddRequirementDtoRequest.class);
            validateRequest(requirementDtoRequest);
            Requirement requirement = EmployerMapper.INSTANCE.requirementToRequirementDto(requirementDtoRequest);

            Vacancy vacancy = new Vacancy(requirementDtoRequest.getVacancyId());
            requirement.setVacancy(vacancy);
            vacancy.add(requirement);

            int id = employerDao.addVacancyRequirement(requirement, vacancy);
            AddRequirementDtoResponse addRequirementDtoResponse = new AddRequirementDtoResponse(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(addRequirementDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteVacancy(UUID token, String requestJson) {
        try {
            Employer employer = getEmployerByToken(token);
            DeleteVacancyDtoRequest vacancyDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteVacancyDtoRequest.class);
            int vacancyId = vacancyDtoRequest.getVacancyId();

            Vacancy vacancy = getVacancyById(vacancyId);
            vacancy.deleteAll();
            employer.delete(vacancy);

            employerDao.deleteVacancy(vacancyId);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteVacancyRequirement(UUID token, String requestJson) {
        try {
            getEmployerByToken(token);
            DeleteRequirementDtoRequest requirementDtoRequest = ServerUtils.getClassFromJson(requestJson, DeleteRequirementDtoRequest.class);
            int requirementId = requirementDtoRequest.getRequirementId();

            Requirement requirement = getRequirementById(requirementId);
            Vacancy vacancy = new Vacancy(employerDao.getIdVacancyByRequirement(requirement));
            //vacancy.delete(requirement);

            employerDao.deleteVacancyRequirement(requirementId);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getRequirementByIdExternal(UUID token, int id) {
        try {
            getEmployerByToken(token);
            Requirement employeeRequirement = getRequirementById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getVacancyRequirementDto(employeeRequirement)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getVacancyByIdExternal(UUID token, int id) {
        try {
            getEmployerByToken(token);
            Vacancy vacancy = getVacancyById(id);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(
                    EmployerMapper.INSTANCE.getVacancyDto(vacancy)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getAllVacancies(UUID token) {
        try {
            getEmployerByToken(token);
            List<Vacancy> vacancies = employerDao.getAllVacancies();
            if (vacancies.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_VACANCIES_ERROR);
            }

            List<GetVacancyDtoResponse> allVacanciesResponse = new ArrayList<>();
            for (Vacancy item: vacancies) {
                allVacanciesResponse.add(EmployerMapper.INSTANCE.getVacancyDto(item));
            }

            GetAllVacanciesDtoResponse allVacanciesDtoResponse = new GetAllVacanciesDtoResponse();
            allVacanciesDtoResponse.setVacancies(allVacanciesResponse);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(allVacanciesDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getAllRequirements(UUID token) {
        try {
            getEmployerByToken(token);
            List<Requirement> requirements = employerDao.getAllRequirements();
            if (requirements.size() == 0) {
                throw new ServerException(ServerErrorCode.GETTING_REQUIREMENTS_ERROR);
            }

            List<GetRequirementDtoResponse> allRequirementsResponse = new ArrayList<>();
            for (Requirement item: requirements) {
                allRequirementsResponse.add(EmployerMapper.INSTANCE.getVacancyRequirementDto(item));
            }

            GetAllRequirementsDtoResponse allRequirementsDtoResponse = new GetAllRequirementsDtoResponse();
            allRequirementsDtoResponse.setRequirementsList(allRequirementsResponse);

            return new ServerResponse(SUCCESS_CODE, GSON.toJson(allRequirementsDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Employer getEmployerByToken(UUID token) throws ServerException {
        if (token == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }
        User user = employerDao.getUserByToken(String.valueOf(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_EXIST);
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

    private Requirement getRequirementById(int id) throws ServerException {
        Requirement requirement = employerDao.getRequirementById(id);
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
