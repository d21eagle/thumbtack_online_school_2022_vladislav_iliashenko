package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public interface EmployerDao {
    int insert(Employer employer) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
    User getUserById(int id);
    int addVacancy(Vacancy vacancy);
    int addVacancyRequirement(Requirement requirement);
    void deleteVacancy(int id);
    void deleteVacancyRequirement(int id);
    Requirement getRequirementById(int id);
    Vacancy getVacancyById(int id);
}
