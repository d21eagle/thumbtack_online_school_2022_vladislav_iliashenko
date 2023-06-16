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
import net.thumbtack.school.hiring.utils.ServerUtils;
import net.thumbtack.school.hiring.utils.Settings;

import java.util.UUID;

public class UserService {
    private static final Gson GSON = new Gson();
    private static final int SUCCESS_CODE = 200;
    private final UserDao userDao = new UserDaoImpl();
    private final Settings settings = Settings.getInstance();

    public ServerResponse loginUser(String requestJson) {
        try {
            LoginUserDtoRequest loginUserDtoRequest = ServerUtils.getClassFromJson(requestJson, LoginUserDtoRequest.class);
            validateRequest(loginUserDtoRequest);
            User user = userDao.getUserByLogin(loginUserDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginUserDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            String uuid;
            if(settings.getDatabaseType().equals("SQL")) {
                uuid = UUID.randomUUID().toString();
                userDao.loginUser(user, uuid);
            }
            else {
                uuid = String.valueOf(userDao.loginUser(user));
            }
            LoginUserDtoResponse loginUserDtoResponse = new LoginUserDtoResponse(UUID.fromString(uuid));
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse logoutUser(String token) {
        try {
            userDao.logoutUser(token);
            return new ServerResponse(SUCCESS_CODE, GSON.toJson(new EmptyResponse()));
        }
        catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private void validateRequest(LoginUserDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }
}
