package net.thumbtack.school.hiring.daoimpl.sql;

import net.thumbtack.school.hiring.mappers.EmployeeMapper;
import net.thumbtack.school.hiring.mappers.EmployerMapper;
import net.thumbtack.school.hiring.mappers.UserMapper;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {
    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected EmployerMapper getEmployerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployerMapper.class);
    }

    protected EmployeeMapper getEmployeeMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployeeMapper.class);
    }
}
