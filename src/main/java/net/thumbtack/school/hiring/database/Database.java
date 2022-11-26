package net.thumbtack.school.hiring.database;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import com.google.common.base.Strings;

public class Database {
    private static Database instance;
    private final Map<String, User> users = new HashMap<>();
    // REVU нет, не надо static . А вдруг еще один экземпляр БД появится ?
    private static final BidiMap<UUID, User> tokens = new DualHashBidiMap<>();

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.LOGIN_ALREADY_USED);
        }
    }

    public User getUserByLogin(String login) throws ServerException {
        // REVU не нужно. В сервисе есть валидация логина в DTO, сюда пустой не дойдет
        if (Strings.isNullOrEmpty(login))
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        return users.get(login);
    }

    public User getUserByToken(UUID token) throws ServerException {
        // REVU и тут не надо. Сервис должен валидировать токен в DTO request
        if (token == null) {
            throw new ServerException(ServerErrorCode.INVALID_TOKEN);
        }
        return tokens.get(token);
    }

    public UUID loginUser(User user) {
        UUID uuid = tokens.getKey(user);
        if (uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        tokens.put(token, user);
        return token;
    }

    public void logoutUser(UUID token) throws ServerException {
        if (tokens.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    public void clear() {
        users.clear();
        tokens.clear();
    }
}
