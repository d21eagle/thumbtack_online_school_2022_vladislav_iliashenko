package net.thumbtack.school.hiring.daoimpl.collections;
import net.thumbtack.school.hiring.dao.collection.RamUserDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.User;
import java.util.UUID;

public class RamUserDaoImpl implements RamUserDao {
    @Override
    public UUID loginUser(User request) {
        return Database.getInstance().loginUser(request);
    }

    @Override
    public void logoutUser(UUID token) throws ServerException {
        Database.getInstance().logoutUser(token);
    }

    @Override
    public User getUserByLogin(String login) {
        return Database.getInstance().getUserByLogin(login);
    }

    @Override
    public void clear() {
        Database.getInstance().clear();
    }
}
