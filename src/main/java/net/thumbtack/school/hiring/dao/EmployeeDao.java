package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    void insert(Employee employee) throws ServerException;
    int addSkill(Skill skill);
    void deleteSkillById(int id);
    User getUserByToken(UUID token);
    User getUserById(int id);
    Skill getSkillById(int id);
}
