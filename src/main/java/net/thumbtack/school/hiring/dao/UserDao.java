package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import java.util.UUID;

public interface UserDao {
    UUID loginUser (User user) throws ServerException;
    void logoutUser (LogoutUserDtoRequest logoutUserDtoRequest) throws ServerException;
    User getUserByLogin(String login) throws ServerException;
    void clear();
}