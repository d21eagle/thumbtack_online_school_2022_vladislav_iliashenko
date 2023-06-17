package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.dao.collection.RamUserDao;
import net.thumbtack.school.hiring.dao.sql.SqlUserDao;
import net.thumbtack.school.hiring.daoimpl.sql.SqlUserDaoImpl;
import net.thumbtack.school.hiring.daoimpl.collections.RamUserDaoImpl;
import net.thumbtack.school.hiring.utils.Settings;

public class DebugService {
    private final SqlUserDao sqlUserDao = new SqlUserDaoImpl();
    private final RamUserDao ramUserDao = new RamUserDaoImpl();
    private final Settings settings = Settings.getInstance();
    public void clear() {
        if (settings.getDatabaseType().equals("SQL")) {
            sqlUserDao.clear();
        }
        else {
            ramUserDao.clear();
        }
    }
}
