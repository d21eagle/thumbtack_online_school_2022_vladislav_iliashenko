package net.thumbtack.school.hiring.service;
import com.google.gson.Gson;
import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.*;
import net.thumbtack.school.hiring.daoimpl.*;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.server.ServerResponse;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.server.ServerUtils;
import java.util.UUID;

public class UserService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private final UserDao userDao = new UserDaoImpl();

    public ServerResponse loginUser(String requestJson) {
        try {
            LoginUserDtoRequest loginUserDtoRequest = ServerUtils.getClassFromJson(requestJson, LoginUserDtoRequest.class);
            validateRequest(loginUserDtoRequest);
            User user = userDao.getUserByLogin(loginUserDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginUserDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = userDao.loginUser(user);
            LoginUserDtoResponse loginUserDtoResponse = new LoginUserDtoResponse(uuid);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse logoutUser(String requestJson) {
        try {
            LogoutUserDtoRequest logoutEmployerDtoRequest = ServerUtils.getClassFromJson(requestJson, LogoutUserDtoRequest.class);
            validateRequest(logoutEmployerDtoRequest);
            userDao.logoutUser(logoutEmployerDtoRequest);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        }
        catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public void clear() {
        userDao.clear();
    }

    private void validateRequest(LoginUserDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }

    private void validateRequest(LogoutUserDtoRequest request) throws ServerException {
        if (request.getToken() == null)
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
    }
}
