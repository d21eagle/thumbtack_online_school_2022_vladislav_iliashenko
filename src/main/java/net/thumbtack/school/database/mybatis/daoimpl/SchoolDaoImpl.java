package net.thumbtack.school.database.mybatis.daoimpl;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.mybatis.dao.SchoolDao;
import net.thumbtack.school.database.mybatis.mappers.GroupMapper;
import net.thumbtack.school.database.mybatis.mappers.SubjectMapper;
import net.thumbtack.school.database.mybatis.mappers.TraineeMapper;
import net.thumbtack.school.database.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class SchoolDaoImpl extends DaoImplBase implements SchoolDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public School insert(School school) {
        LOGGER.debug("Вставка школы {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить школу {}, {}", school, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return school;
    }

    @Override
    public School getById(int id) {
        School school;
        try (SqlSession sqlSession = getSession()) {
            try {
                school = getSchoolMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить школу по id {}, {}", id, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return school;
    }

    @Override
    public List<School> getAllLazy() {
        List<School> schools;
        try (SqlSession sqlSession = getSession()) {
            try {
                schools = getSchoolMapper(sqlSession).getAllLazy();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить список всех школ {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return schools;
    }

    @Override
    public List<School> getAllUsingJoin() {
        List<School> schools;
        try (SqlSession sqlSession = getSession()) {
            try {
                schools = sqlSession.selectList("net.thumbtack.school.database.mybatis.mappers.SchoolMapper.getAllUsingJoin");
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось получить список всех школ {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return schools;
    }

    @Override
    public void update(School school) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).update(school);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось изменить школу {}, {}", school, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(School school) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).delete(school);
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить школу {}, {}", school, e);
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
                getSchoolMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось удалить все школы {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public School insertSchoolTransactional(School school2018) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school2018);
                GroupMapper groupMapper = getGroupMapper(sqlSession);
                TraineeMapper traineeMapper = getTraineeMapper(sqlSession);
                SubjectMapper subjectMapper = getSubjectMapper(sqlSession);
                for (Group group : school2018.getGroups()) {
                    groupMapper.insert(school2018, group);
                    group.getTrainees().forEach(trainee -> {
                        traineeMapper.insert(group, trainee);
                    });
                    for (Subject subject : group.getSubjects()) {
                        subjectMapper.insert(subject);
                        groupMapper.addSubjectToGroup(group, subject);
                    }
                }
            } catch (RuntimeException e) {
                LOGGER.info("Не удалось вставить школу {}, {}", school2018, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return school2018;
    }
}