package net.thumbtack.school.hiring.daoimpl.collections;
import net.thumbtack.school.hiring.dao.collection.RamEmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.database.Database;
import java.util.*;

public class RamEmployeeDaoImpl implements RamEmployeeDao {

    @Override
    public int insert(Employee employee) throws ServerException {
        return Database.getInstance().insert(employee);
    }

    @Override
    public int addSkill(Skill skill, Employee employee) {
        return Database.getInstance().addSkill(skill, employee);
    }

    @Override
    public void deleteSkill(int id) throws ServerException {
        Database.getInstance().deleteSkill(id);
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public Skill getSkillById(int id) {
        return Database.getInstance().getSkillById(id);
    }

    @Override
    public List<Skill> getAllSkills() {
        return Database.getInstance().getAllSkills();
    }
}
