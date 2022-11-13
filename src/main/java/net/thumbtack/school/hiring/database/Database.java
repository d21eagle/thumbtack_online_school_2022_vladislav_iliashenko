package net.thumbtack.school.hiring.database;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Database {
    private static Database instance;
    private final Map<String, User> users = new HashMap<>();
    private final BidiMap<UUID, User> tokens = new DualHashBidiMap<>();

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.EXIST_LOGIN);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    public UUID getToken(String login) {
        User user = getUserByLogin(login);
        return tokens.getKey(user);
    }
}
