package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public interface EmployerDao {
    void insert(Employer employer) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
    User getUserById(int id);
    int addVacancy(Vacancy vacancy);
    int addEmployeeRequirement(EmployeeRequirement requirement);
    void deleteVacancyById(int id);
    void deleteEmployeeRequirementById(int id);
    EmployeeRequirement getRequirementById(int id);
    Vacancy getVacancyById(int id);
}
