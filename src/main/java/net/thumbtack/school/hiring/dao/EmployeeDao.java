package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    int insert(Employee employee) throws ServerException;
    int addSkill(Skill skill);
    void deleteSkill(int id);
    User getUserByToken(UUID token);
    User getUserById(int id);
    Skill getSkillById(int id);
}
