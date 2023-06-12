package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class EmployeeDaoImpl extends DaoImplBase implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
    @Override
    public int insert(Employee employee) throws ServerException {
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

    @Override
    public User getUserByToken(String token) {
        LOGGER.debug("DAO get employee by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployeeMapper(sqlSession).getUserByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employee by token {}, {}", ex, token);
                throw ex;
            }
        }
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

    @Override
    public void deleteSkill(int id) {
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

    @Override
    public Skill getSkillById(int id) {
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

    @Override
    public List<Skill> getAllSkills() {
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
}
