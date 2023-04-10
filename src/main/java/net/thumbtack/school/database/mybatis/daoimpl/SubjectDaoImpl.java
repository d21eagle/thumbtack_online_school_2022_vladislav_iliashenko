package net.thumbtack.school.database.mybatis.daoimpl;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.mybatis.dao.SubjectDao;
import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class SubjectDaoImpl extends DaoImplBase implements SubjectDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public Subject insert(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).insert(subject);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить предмет {}, {}", subject, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return subject;
    }

    @Override
    public Subject getById(int id) {
        Subject subject;
        try (SqlSession sqlSession = getSession()) {
            try {
                subject = getSubjectMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить предмет по id {}, {}", id, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return subject;
    }

    @Override
    public List<Subject> getAll() {
        List<Subject> subjects;
        try (SqlSession sqlSession = getSession()) {
            try {
                subjects = getSubjectMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить список всех предметов {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return subjects;
    }

    @Override
    public Subject update(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).update(subject);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось изменить предмет {}, {}", subject, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return subject;
    }

    @Override
    public void delete(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).delete(subject);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить предмет {}, {}", subject, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить все предметы {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}