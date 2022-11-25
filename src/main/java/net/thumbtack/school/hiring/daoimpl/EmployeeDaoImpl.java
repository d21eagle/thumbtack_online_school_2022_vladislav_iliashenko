package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;
import net.thumbtack.school.hiring.dto.request.*;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void insert(Employee employee) throws ServerException {
        Database.getInstance().insert(employee);
    }

    @Override
    public UUID loginUser(User request) {
        return Database.getInstance().loginUser(request);
    }

    @Override
    public void logoutUser(LogoutEmployeeDtoRequest dtoRequest) throws ServerException {
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
