package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public void insert(Employee employee) throws ServerException {
        Database.getInstance().insert(employee);
    }

    @Override
    public void addSkill(Employee employee, Skill skill) {
        Database.getInstance().addSkill(employee, skill);
    }

    @Override
    public void deleteSkill(Employee employee, Skill skill) throws ServerException {
        Database.getInstance().deleteSkill(employee, skill);
    }

    @Override
    public void deleteSkillById(int id) {
        Database.getInstance().deleteSkillById(id);
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public User getUserById(int id) {
        return Database.getInstance().getUserById(id);
    }

    @Override
    public Skill getSkillById(int id) {
        return Database.getInstance().getSkillById(id);
    }
}
