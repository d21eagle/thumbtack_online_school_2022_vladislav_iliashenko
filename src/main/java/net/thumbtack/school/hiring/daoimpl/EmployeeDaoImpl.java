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
    public UUID loginUser(User request) throws ServerException {
        return Database.getInstance().loginEmployee(request);
    }

    @Override
    public void logoutUser(LogoutEmployeeDtoRequest dtoRequest) throws ServerException {
        Database.getInstance().logoutEmployee(dtoRequest.getToken());
    }

    @Override
    public User getUserByLogin(String login) throws ServerException {
        return Database.getInstance().getUserByLogin(login);
    }
}
