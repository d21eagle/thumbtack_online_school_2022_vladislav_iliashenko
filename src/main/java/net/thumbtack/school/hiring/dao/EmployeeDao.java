package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    int insert(Employee employee) throws ServerException;

    Integer getIdByEmployee(String token);

    int addSkill(Skill skill, Employee employee);
    void deleteSkill(int id) throws ServerException;
    User getUserByToken(String token);
    Skill getSkillById(int id);
    List<Skill> getAllSkills();
}
