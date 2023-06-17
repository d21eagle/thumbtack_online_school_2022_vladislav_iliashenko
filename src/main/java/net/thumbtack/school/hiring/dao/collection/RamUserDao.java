package net.thumbtack.school.hiring.dao.collection;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import java.util.UUID;

public interface RamUserDao {
    UUID loginUser (User user) throws ServerException;
    void logoutUser (UUID token) throws ServerException;
    User getUserByLogin(String login) throws ServerException;
    void clear();
}
