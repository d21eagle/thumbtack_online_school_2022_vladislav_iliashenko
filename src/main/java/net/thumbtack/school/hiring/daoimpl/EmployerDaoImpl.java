package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.List;
import java.util.UUID;

public class EmployerDaoImpl implements EmployerDao {
    @Override
    public int insert(Employer employer) throws ServerException {
        return Database.getInstance().insert(employer);
    }

    @Override
    public User getUserByToken(UUID token) throws ServerException {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public User getUserById(int id) {
        return Database.getInstance().getUserById(id);
    }

    @Override
    public int addVacancy(Vacancy vacancy) {
        return Database.getInstance().addVacancy(vacancy);
    }

    @Override
    public int addVacancyRequirement(Requirement requirement) {
        return Database.getInstance().addVacancyRequirement(requirement);
    }
    @Override
    public void deleteVacancy(int id) {
        Database.getInstance().deleteVacancy(id);
    }
    @Override
    public void deleteVacancyRequirement(int id) {
        Database.getInstance().deleteVacancyRequirement(id);
    }

    @Override
    public Requirement getRequirementById(int id) {
        return Database.getInstance().getRequirementById(id);
    }

    @Override
    public Vacancy getVacancyById(int id) {
        return Database.getInstance().getVacancyById(id);
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        return Database.getInstance().getAllVacancies();
    }

    @Override
    public List<Requirement> getAllRequirements() {
        return Database.getInstance().getAllRequirements();
    }
}
