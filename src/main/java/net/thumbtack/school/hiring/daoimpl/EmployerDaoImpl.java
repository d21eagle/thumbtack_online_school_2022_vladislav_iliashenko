package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class EmployerDaoImpl extends DaoImplBase implements EmployerDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
    @Override
    public int insert(Employer employer) throws ServerException {
        LOGGER.debug("DAO insert employer {}", employer);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                id = getUserMapper(sqlSession).insert(employer);
                getEmployerMapper(sqlSession).insert(employer);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert Employer {}", employer, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }

    @Override
    public User getUserByToken(String token) {
        LOGGER.debug("DAO get employer by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getUserByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employer by token {}", token, ex);
                throw ex;
            }
        }
    }

    @Override
    public int addVacancy(Vacancy vacancy, Employer employer) {
        LOGGER.debug("DAO insert vacancy to employer {}, {}", vacancy, employer);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).addVacancy(vacancy, employer);
                id = getEmployerMapper(sqlSession).getIdVacancyByInfo(vacancy, employer);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert vacancy to employer {}, {}", vacancy, employer, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }

    @Override
    public int addVacancyRequirement(Requirement requirement, Vacancy vacancy) {
        LOGGER.debug("DAO insert vacancyRequirement in vacancy by id {}, {}", requirement, vacancy);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).addVacancyRequirement(requirement, vacancy);
                id = getEmployerMapper(sqlSession).getIdVacancyRequirementByInfo(requirement, vacancy);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert vacancyRequirement in vacancy by id {}, {}", requirement, vacancy, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }
    @Override
    public void deleteVacancy(int id) {
        LOGGER.debug("DAO delete vacancy by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).deleteVacancy(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete vacancy by id {}", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
    @Override
    public void deleteVacancyRequirement(int id) {
        LOGGER.debug("DAO delete vacancyRequirement by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).deleteVacancyRequirement(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete vacancyRequirement {}", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Requirement getRequirementById(int id) {
        LOGGER.debug("DAO get vacancyRequirement by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getRequirementById(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancyRequirement by id {}, {}", ex, id);
                throw ex;
            }
        }
    }

    @Override
    public Vacancy getVacancyById(int id) {
        LOGGER.debug("DAO get vacancy by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getVacancyById(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancy by id {}, {}", ex, id);
                throw ex;
            }
        }
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        LOGGER.debug("DAO get all vacancies");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getAllVacancies();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all vacancies", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Requirement> getAllRequirements() {
        LOGGER.debug("DAO get all vacancyRequirements");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getAllRequirements();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all vacancies", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Employee> getEmployeesByVacancyRequirement(Requirement requirement) {
        LOGGER.debug("DAO get employees by vacancyRequirement {}", requirement);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getEmployeesByVacancyRequirement(requirement);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employees by vacancyRequirement {}, {}", ex, requirement);
                throw ex;
            }
        }
    }

    @Override
    public int getIdByEmployer(String token) {
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
    public int getIdVacancyByRequirement(Requirement requirement) {
        LOGGER.debug("DAO delete session by token {} ", requirement);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getIdVacancyByRequirement(requirement);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete session by token {}, {}", ex, requirement);
                throw ex;
            }
        }
    }
}
