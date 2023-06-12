package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;

public interface UserDao {
    void loginUser (User user, String uuid) throws ServerException;

    void logoutUser(String token) throws ServerException;

    User getUserByLogin(String login) throws ServerException;
    void clear();
}
