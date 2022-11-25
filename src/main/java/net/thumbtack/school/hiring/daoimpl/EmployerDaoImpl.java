package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public class EmployerDaoImpl implements EmployerDao {

    @Override
    public void insert(Employer employer) throws ServerException {
        Database.getInstance().insert(employer);
    }

    @Override
    public UUID loginUser(User request) {
        return Database.getInstance().loginUser(request);
    }

    @Override
    public void logoutUser(LogoutEmployerDtoRequest dtoRequest) throws ServerException {
        Database.getInstance().logoutUser(dtoRequest.getToken());
    }

    @Override
    public User getUserByLogin(String login) throws ServerException {
        return Database.getInstance().getUserByLogin(login);
    }

    @Override
    public User getUserByToken(UUID token) throws ServerException {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public void clear() {
        Database.getInstance().clear();
    }
}
