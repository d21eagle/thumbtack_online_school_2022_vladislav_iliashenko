package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    int insert(Employee employee) throws ServerException;
    Employee getEmployeeByToken(String token);
    User getUserByToken(UUID token);
    Integer getIdByEmployee(String token);
    int addSkill(Skill skill, Employee employee);
    void deleteSkill(int id) throws ServerException;
    Skill getSkillById(int id);
    List<Skill> getAllSkills();
}
