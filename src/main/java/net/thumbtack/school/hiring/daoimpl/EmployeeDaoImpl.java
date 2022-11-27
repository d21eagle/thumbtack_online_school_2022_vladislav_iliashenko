package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public void insert(Employee employee) throws ServerException {
        Database.getInstance().insert(employee);
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }
}
