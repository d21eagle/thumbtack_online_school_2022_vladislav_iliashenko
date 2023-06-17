package net.thumbtack.school.hiring.dao.sql;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.List;

public interface SqlEmployerDao {
    int insert(Employer employer) throws ServerException;
    Employer getEmployerByToken(String token);
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
}
