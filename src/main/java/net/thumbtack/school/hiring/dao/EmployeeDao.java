package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    void insert(Employee employee) throws ServerException;
    void addSkill(Employee employee, Skill skill);
    void deleteSkill(Employee employee, Skill skill) throws ServerException;
    void deleteSkillById(int id);
    User getUserByToken(UUID token) throws ServerException;
    User getUserById(int id);
    Skill getSkillById(int id);
}
