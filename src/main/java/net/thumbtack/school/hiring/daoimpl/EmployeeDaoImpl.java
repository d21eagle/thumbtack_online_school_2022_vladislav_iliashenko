package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class EmployeeDaoImpl extends DaoImplBase implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
    private final Settings settings = Settings.getInstance();
    @Override
    public int insert(Employee employee) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
            int id;
            LOGGER.debug("DAO insert employee {}", employee);
            try (SqlSession sqlSession = getSession()) {
                try {
                    id = getUserMapper(sqlSession).insert(employee);
                    getEmployeeMapper(sqlSession).insert(employee);
                } catch (RuntimeException ex) {
                    LOGGER.info("DAO can't insert Employee {}, {}", ex, employee);
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
            return id;
        }
        else {
            return Database.getInstance().insert(employee);
        }
    }

    @Override
    public Employee getEmployeeByToken(String token) {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO get employee by token {}", token);
            try (SqlSession sqlSession = getSession()) {
                try {
                    User user = getUserMapper(sqlSession).getUserByToken(token);
                    if (user == null) return null;
                    return getEmployeeMapper(sqlSession).getEmployeeById(user.getUserId());
                } catch (RuntimeException ex) {
                    LOGGER.info("DAO can't get employee by token {}, {}", ex, token);
                    throw ex;
                }
            }
        }
        else {
            return (Employee) Database.getInstance().getUserByToken(UUID.fromString(token));
        }
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public Integer getIdByEmployee(String token) {
        LOGGER.debug("DAO delete session by token {} ", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getUserMapper(sqlSession).getIdByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete session by token {}, {}", ex, token);
                throw ex;
            }
        }
    }

    @Override
    public int addSkill(Skill skill, Employee employee) {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO insert skill to employee {}, {}",skill, employee);
            int id;
            try (SqlSession sqlSession = getSession()) {
                try {
                    getEmployeeMapper(sqlSession).addSkill(skill, employee);
                    id = getEmployeeMapper(sqlSession).getIdSkillByInfo(skill, employee);
                }  catch (RuntimeException ex) {
                    LOGGER.info("DAO can't insert skill to employee {}, {}, {}", ex, employee, skill);
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
            return id;
        }
        else {
            return Database.getInstance().addSkill(skill, employee);
        }
    }

    @Override
    public void deleteSkill(int id) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO delete skill by {}",id);
            try (SqlSession sqlSession = getSession()) {
                try {
                    getEmployeeMapper(sqlSession).deleteSkill(id);
                }  catch (RuntimeException ex) {
                    LOGGER.info("DAO can't delete skill by id {}, {}", ex, id);
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
        }
        else {
            Database.getInstance().deleteSkill(id);
        }
    }

    @Override
    public Skill getSkillById(int id) {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO get skill by id {}", id);
            try (SqlSession sqlSession = getSession()) {
                try {
                    return getEmployeeMapper(sqlSession).getSkillById(id);
                }  catch (RuntimeException ex) {
                    LOGGER.info("DAO can't get get skill by id {}, {}", ex, id);
                    throw ex;
                }
            }
        }
        else {
            return Database.getInstance().getSkillById(id);
        }
    }

    @Override
    public List<Skill> getAllSkills() {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO get all skills");
            try (SqlSession sqlSession = getSession()) {
                try {
                    return getEmployeeMapper(sqlSession).getAllSkills();
                }  catch (RuntimeException ex) {
                    LOGGER.info("DAO can't delete skills", ex);
                    throw ex;
                }
            }
        }
        else {
            return Database.getInstance().getAllSkills();
        }
    }
}
