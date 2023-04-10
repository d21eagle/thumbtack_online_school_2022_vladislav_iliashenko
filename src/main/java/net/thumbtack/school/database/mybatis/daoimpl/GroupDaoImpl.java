package net.thumbtack.school.database.mybatis.daoimpl;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.GroupDao;
import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class GroupDaoImpl extends DaoImplBase implements GroupDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public Group insert(School school, Group group) {
        LOGGER.debug("Вставка группы {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).insert(school, group);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить группу {}, {}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return group;
    }

    @Override
    public Group update(Group group) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).update(group);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось изменить группу {}, {}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return group;
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups;
        try (SqlSession sqlSession = getSession()) {
            try {
                groups = getGroupMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить список групп {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return groups;
    }

    @Override
    public void delete(Group group) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).delete(group);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить группу {}, {}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Trainee moveTraineeToGroup(Group group, Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).moveTraineeToGroup(group, trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось перевести ученика в группу {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public void deleteTraineeFromGroup(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).deleteTraineeFromGroup(trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить ученика из группы {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addSubjectToGroup(Group group, Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).addSubjectToGroup(group, subject);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось добавить предмет к группе {}, {}", subject, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}