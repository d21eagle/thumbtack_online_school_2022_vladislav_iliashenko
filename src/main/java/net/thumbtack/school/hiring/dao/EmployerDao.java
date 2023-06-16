package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EmployerDao {
    int insert(Employer employer) throws ServerException;
    Employer getEmployerByToken(String token);
    User getUserByToken(UUID token);
    int addVacancy(Vacancy vacancy, Employer employer);
    int addVacancyRequirement(Requirement requirement, Vacancy vacancy);
    void deleteVacancy(int id) throws ServerException;
    void deleteVacancyRequirement(int id) throws ServerException;
    Requirement getRequirementById(int id);
    Vacancy getVacancyById(int id);
    List<Vacancy> getAllVacancies();
    List<Requirement> getAllRequirements();
    List<Employee> getEmployeesByVacancyRequirement(Requirement requirement);
    int getIdByEmployer(String token);
    int getIdVacancyByRequirement(Requirement requirement);
    Set<Employee> getEmployeesByRequirements(List<Requirement> requirements);
}
