package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import java.util.UUID;

public class UserDaoImpl implements UserDao {
    @Override
    public UUID loginUser(User request) {
        return Database.getInstance().loginUser(request);
    }

    @Override
    public void logoutUser(LogoutUserDtoRequest dtoRequest) throws ServerException {
        Database.getInstance().logoutUser(dtoRequest.getToken());
    }

    @Override
    public User getUserByLogin(String login) throws ServerException {
        return Database.getInstance().getUserByLogin(login);
    }

    @Override
    public void clear() {
        Database.getInstance().clear();
    }
}
