package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;

import java.util.UUID;

public interface UserDao {
    void loginUser (User user, String uuid) throws ServerException;
    UUID loginUser(User request);
    void logoutUser(String token) throws ServerException;
    User getUserByLogin(String login) throws ServerException;
    void clear();
}
