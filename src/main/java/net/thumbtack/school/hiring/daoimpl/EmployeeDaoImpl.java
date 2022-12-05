package net.thumbtack.school.hiring.daoimpl;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public int insert(Employee employee) throws ServerException {
        return Database.getInstance().insert(employee);
    }

    @Override
    public int addSkill(Skill skill) {
        return Database.getInstance().addSkill(skill);
    }

    @Override
    public void deleteSkill(int id) {
        Database.getInstance().deleteSkill(id);
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
