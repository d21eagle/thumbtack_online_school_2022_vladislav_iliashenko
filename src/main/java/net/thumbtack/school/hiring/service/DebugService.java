package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.daoimpl.UserDaoImpl;

public class DebugService {
    private final UserDao userDao = new UserDaoImpl();
    public void clear() {
        userDao.clear();
    }
}
