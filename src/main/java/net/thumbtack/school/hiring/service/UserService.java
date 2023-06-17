package net.thumbtack.school.hiring.service;

import com.google.common.base.Strings;
import net.thumbtack.school.hiring.dao.collection.RamUserDao;
import net.thumbtack.school.hiring.dao.sql.SqlUserDao;
import net.thumbtack.school.hiring.daoimpl.collections.RamUserDaoImpl;
import net.thumbtack.school.hiring.daoimpl.sql.SqlUserDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.utils.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class UserService {
    private final SqlUserDao sqlUserDao = new SqlUserDaoImpl();
    private final RamUserDao ramUserDao = new RamUserDaoImpl();
    private final Settings settings = Settings.getInstance();

    public Response loginUser(String requestJson) {
        try {
            LoginUserDtoRequest loginUserDtoRequest = ServerUtils.getClassFromJson(requestJson, LoginUserDtoRequest.class);
            validateRequest(loginUserDtoRequest);

            User user;
            String uuid;
            if (settings.getDatabaseType().equals("SQL")) {
                user = sqlUserDao.getUserByLogin(loginUserDtoRequest.getLogin());
                if (user == null || !user.getPassword().equals(loginUserDtoRequest.getPassword())) {
                    throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
                }
                uuid = UUID.randomUUID().toString();
                sqlUserDao.loginUser(user, uuid);
            }
            else {
                user = ramUserDao.getUserByLogin(loginUserDtoRequest.getLogin());
                if (user == null || !user.getPassword().equals(loginUserDtoRequest.getPassword())) {
                    throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
                }
                uuid = String.valueOf(ramUserDao.loginUser(user));
            }
            LoginUserDtoResponse loginUserDtoResponse = new LoginUserDtoResponse(UUID.fromString(uuid));
            return Response.ok(loginUserDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    public Response logoutUser(String token) {
        try {
            if (settings.getDatabaseType().equals("SQL")) {
                sqlUserDao.logoutUser(token);
            }
            else {
                ramUserDao.logoutUser(UUID.fromString(token));
            }
            return Response.ok(new EmptyResponse(), MediaType.APPLICATION_JSON).build();
        }
        catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    private void validateRequest(LoginUserDtoRequest request) throws ServerException {
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword()))
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
    }
}
