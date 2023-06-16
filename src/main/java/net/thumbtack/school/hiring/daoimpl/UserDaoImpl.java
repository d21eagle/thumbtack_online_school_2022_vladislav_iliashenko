package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import net.thumbtack.school.hiring.utils.Settings;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UserDaoImpl extends DaoImplBase implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
    private final Settings settings = Settings.getInstance();
    @Override
    public void loginUser(User request, String uuid) {
        LOGGER.debug("DAO insert session user {} ", request);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).loginUser(request, uuid);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert user by id {}, {}", ex, request);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public UUID loginUser(User request) {
        return Database.getInstance().loginUser(request);
    }

    @Override
    public void logoutUser(String token) throws ServerException {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO delete session by token {} ", token);
            try (SqlSession sqlSession = getSession()) {
                try {
                    getUserMapper(sqlSession).logoutUser(token);
                } catch (RuntimeException ex) {
                    LOGGER.info("DAO can't delete session by token {}, {}", ex, token);
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
        }
        else {
            Database.getInstance().logoutUser(UUID.fromString(token));
        }
    }

    @Override
    public User getUserByLogin(String login) {
        if(settings.getDatabaseType().equals("SQL")) {
            LOGGER.debug("DAO get user by login {}", login);
            try (SqlSession sqlSession = getSession()){
                try {
                    return getUserMapper(sqlSession).getUserByLogin(login);
                } catch (RuntimeException ex) {
                    LOGGER.info("DAO can't get User by login {}, {}", login, ex);
                    throw ex;
                }
            }
        }
        else {
            return Database.getInstance().getUserByLogin(login);
        }
    }

    @Override
    public void clear() {
        if(settings.getDatabaseType().equals("SQL")) {
            try(SqlSession sqlSession = getSession()){
                try{
                    getUserMapper(sqlSession).clear();
                } catch (RuntimeException ex){
                    LOGGER.info("Can't clear database");
                    sqlSession.rollback();
                    throw ex;
                }
                sqlSession.commit();
            }
        }
        else {
            Database.getInstance().clear();
        }
    }
}
