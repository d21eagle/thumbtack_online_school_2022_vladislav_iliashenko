package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.mapper.EmployerMapper;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.server.ServerUtils;

public class EmployerService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int MIN_LOGIN = 8;
    private static final int MIN_PASSWORD = 8;
    private final EmployerDao employerDao = new EmployerDaoImpl();

    public ServerResponse registerEmployee(String requestJson) throws JsonSyntaxException {
        try {
            RegisterEmployerDtoRequest registerDtoRequest = ServerUtils.getClassFromJson(requestJson, RegisterEmployerDtoRequest.class);
            validateRequest(registerDtoRequest);
            Employer employer = EmployerMapper.INSTANCE.employerToEmployerDto(registerDtoRequest);
            employerDao.insert(employer);
            EmptyResponse emptyResponse = new EmptyResponse();
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(emptyResponse));
        } catch (ServerException e) {
            ErrorResponse errorResponse = new ErrorResponse(e);
            return new ServerResponse(ERROR_CODE, GSON.toJson(errorResponse));
        }
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

}
