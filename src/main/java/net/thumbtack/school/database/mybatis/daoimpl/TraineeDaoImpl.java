package net.thumbtack.school.database.mybatis.daoimpl;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.TraineeDao;
import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class TraineeDaoImpl extends DaoImplBase implements TraineeDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public Trainee insert(Group group, Trainee trainee) {
        LOGGER.debug("Вставка ученика {}", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).insert(group, trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить ученика {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public Trainee getById(int id) {
        Trainee trainee;
        try (SqlSession sqlSession = getSession()) {
            try {
                trainee = getTraineeMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить ученика по id {}, {}", id, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public List<Trainee> getAll() {
        List<Trainee> trainees;
        try (SqlSession sqlSession = getSession()) {
            try {
                trainees = getTraineeMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить всех учеников {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainees;
    }

    @Override
    public Trainee update(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).update(trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось изменить ученика {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating) {
        List<Trainee> trainees;
        try (SqlSession sqlSession = getSession()) {
            try {
                trainees = getTraineeMapper(sqlSession).getAllWithParams(firstName, lastName, rating);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить список всех учеников {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainees;
    }

    @Override
    public void batchInsert(List<Trainee> trainees) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).batchInsert(trainees);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить список учеников {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).delete(trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить ученика {}, {}", trainee, e);
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
                getTraineeMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить всех учеников {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}