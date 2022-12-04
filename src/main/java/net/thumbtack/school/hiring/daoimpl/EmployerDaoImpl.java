package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public class EmployerDaoImpl implements EmployerDao {
    @Override
    public void insert(Employer employer) throws ServerException {
        Database.getInstance().insert(employer);
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public User getUserById(int id) {
        return Database.getInstance().getUserById(id);
    }

    @Override
    public EmployeeRequirement getRequirementById(int id) {
        return Database.getInstance().getRequirementById(id);
    }

    @Override
    public Vacancy getVacancyById(int id) {
        return Database.getInstance().getVacancyById(id);
    }
}
