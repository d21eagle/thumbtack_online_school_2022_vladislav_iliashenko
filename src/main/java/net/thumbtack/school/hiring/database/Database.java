package net.thumbtack.school.hiring.database;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Database {

    private static final Database database = new Database();
    private final Map<String, User> users = new HashMap<>();
    private final BidiMap<UUID, User> tokens = new DualHashBidiMap<>();

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.EXIST_LOGIN);
        }
    }

    // REVU посмотрите, как правильно оформить синглетон
    // https://habr.com/ru/post/129494/
    // 1 Synchronized Accessor
    public static Database getDatabase() {
        return database;
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    // REVU имя метода не соответствует тому, что он делает
    public UUID getTokenByUser(String login) {
        User user = getUserByLogin(login);
        return tokens.getKey(user);
    }
}
