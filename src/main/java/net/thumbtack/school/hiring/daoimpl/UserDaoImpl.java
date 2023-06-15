package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl extends DaoImplBase implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);
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
    public void logoutUser(String token) throws ServerException {
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

    @Override
    public User getUserByLogin(String login) {
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

    @Override
    public void clear() {
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
}
