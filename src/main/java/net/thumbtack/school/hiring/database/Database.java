package net.thumbtack.school.hiring.database;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Database {
    private static Database instance;
    private final Map<String, User> users = new HashMap<>();
    private final BidiMap<UUID, User> tokens = new DualHashBidiMap<>();

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

    public void addSkill(Employee employee, Skill skill) {
        employee.getSkills().add(skill);
    }

    public void deleteSkill(Employee employee, Skill skill) throws ServerException {
        if (employee.getSkills().isEmpty()) {
            throw new ServerException(ServerErrorCode.EMPTY_SKILLS);
        }
        employee.getSkills().remove(skill);
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    public User getUserByToken(UUID token) {
        return tokens.get(token);
    }

    public void clear() {
        users.clear();
        tokens.clear();
    }
}
