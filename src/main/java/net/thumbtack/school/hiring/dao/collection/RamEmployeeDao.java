package net.thumbtack.school.hiring.dao.collection;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface RamEmployeeDao {
    int insert(Employee employee) throws ServerException;
    int addSkill(Skill skill, Employee employee);
    void deleteSkill(int id) throws ServerException;
    User getUserByToken(UUID token);
    Skill getSkillById(int id);
    List<Skill> getAllSkills();
}
