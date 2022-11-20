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
    private static final BidiMap<UUID, User> tokens = new DualHashBidiMap<>();

    // REVU уберите. Показывать все токены миру незачем
    public static BidiMap<UUID, User> getTokens() {
        return tokens;
    }

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.EXIST_LOGIN);
        }
    }

    // REVU это в самом начале, а потом методы. Это псевдоконструктор
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // REVU верно, но проверьте, если проверка на несуществующий логин. Тест!
    public User getUserByLogin(String login) {
        return users.get(login);
    }

    // REVU едва ли нужен. Зачем по логину токен получать ?
    // логин - только для операции "Логин"
    // а там токен возвращается
    public UUID getToken(String login) {
        User user = getUserByLogin(login);
        return tokens.getKey(user);
    }

    public UUID loginEmployee(User user) throws ServerException {
        UUID uuid = tokens.getKey(user);
        if (uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        tokens.put(token, user);
        return token;
    }

    // REVU то же самое
    // а поэтому хватит и одного и назвать его loginUser
    public UUID loginEmployer(User user) throws ServerException {
        UUID uuid = tokens.getKey(user);
        if (uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        tokens.put(token, user);
        return token;
    }

    // REVU аналогично
    public void logoutEmployee(UUID token) throws ServerException {
        if (tokens.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    public void logoutEmployer(UUID token) throws ServerException {
        if (tokens.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }
}
