package net.thumbtack.school.hiring.daoimpl.collections;
import net.thumbtack.school.hiring.dao.collection.RamEmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RamEmployerDaoImpl implements RamEmployerDao {
    @Override
    public int insert(Employer employer) throws ServerException {
        return Database.getInstance().insert(employer);
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
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
    public void deleteVacancy(int id) throws ServerException {
        Database.getInstance().deleteVacancy(id);
    }
    @Override
    public void deleteVacancyRequirement(int id) throws ServerException {
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

    @Override
    public Set<Employee> getEmployeesByRequirements(List<Requirement> requirements) {
        return Database.getInstance().getEmployeesByRequirements(requirements);
    }
}
