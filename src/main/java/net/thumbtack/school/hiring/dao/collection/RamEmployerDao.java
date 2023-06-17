package net.thumbtack.school.hiring.dao.collection;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RamEmployerDao {
    int insert(Employer employer) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;

    int addVacancy(Vacancy vacancy);
    int addVacancyRequirement(Requirement requirement);
    void deleteVacancy(int id) throws ServerException;
    void deleteVacancyRequirement(int id) throws ServerException;
    Requirement getRequirementById(int id);
    Vacancy getVacancyById(int id);
    List<Vacancy> getAllVacancies();
    List<Requirement> getAllRequirements();
    Set<Employee> getEmployeesByRequirements(List<Requirement> requirements);
}
