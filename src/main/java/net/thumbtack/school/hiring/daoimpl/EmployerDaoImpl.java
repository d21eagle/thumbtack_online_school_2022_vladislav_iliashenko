package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class EmployerDaoImpl extends DaoImplBase implements EmployerDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
    private final Settings settings = Settings.getInstance();
    @Override
    public int insert(Employer employer) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().insert(employer);
        }
    }

    @Override
    public Employer getEmployerByToken(String token) {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO get employer by token {}", token);
            try (SqlSession sqlSession = getSession()) {
                try {
                    User user = getUserMapper(sqlSession).getUserByToken(token);
                    if (user == null) return null;
                    return getEmployerMapper(sqlSession).getEmployerById(user.getUserId());
                } catch (RuntimeException ex) {
                    LOGGER.info("DAO can't get employer by token {}", token, ex);
                    throw ex;
                }
            }
        }
        else {
            return (Employer) Database.getInstance().getUserByToken(UUID.fromString(token));
        }
    }

    @Override
    public User getUserByToken(UUID token) {
        return Database.getInstance().getUserByToken(token);
    }

    @Override
    public int addVacancy(Vacancy vacancy, Employer employer) {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().addVacancy(vacancy);
        }
    }

    @Override
    public int addVacancyRequirement(Requirement requirement, Vacancy vacancy) {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().addVacancyRequirement(requirement);
        }
    }
    @Override
    public void deleteVacancy(int id) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            Database.getInstance().deleteVacancy(id);
        }
    }
    @Override
    public void deleteVacancyRequirement(int id) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            Database.getInstance().deleteVacancyRequirement(id);
        }
    }

    @Override
    public Requirement getRequirementById(int id) {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().getRequirementById(id);
        }
    }

    @Override
    public Vacancy getVacancyById(int id) {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().getVacancyById(id);
        }
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().getAllVacancies();
        }
    }

    @Override
    public List<Requirement> getAllRequirements() {
        if(settings.getDatabaseType().equals("SQL")) {
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
        else {
            return Database.getInstance().getAllRequirements();
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
    public Set<Employee> getEmployeesByRequirements(List<Requirement> requirements) {
        return Database.getInstance().getEmployeesByRequirements(requirements);
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
