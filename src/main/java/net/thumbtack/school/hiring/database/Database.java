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
    private final Map<Integer, User> userMap = new HashMap<>();
    private final Map<Integer, Skill> skillMap = new HashMap<>();
    private final Map<Integer, EmployeeRequirement> requirementMap = new HashMap<>();
    private final Map<Integer, Vacancy> vacancyMap = new HashMap<>();
    private int nextUserId = 1;

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
        // REVU зачем 2 раза ++ ?
        user.setId(nextUserId++);
        userMap.put(nextUserId++, user);
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
        // REVU а эту строчку можно было в сервисе выполнить. Это не работа БД, а изменение класса модели
        employee.getSkills().add(skill);
        // REVU зачем 2 раза ++ ?
        skill.setId(nextUserId++);
        skillMap.put(nextUserId++ ,skill);
    }

    public void deleteSkill(Employee employee, Skill skill) throws ServerException {
        // REVU isEmpty не нужна, remove сама скажет
        if (employee.getSkills().isEmpty()) {
            throw new ServerException(ServerErrorCode.EMPTY_SKILLS);
        }
        employee.getSkills().remove(skill);
    }

    // REVU а если нет такого id ?
    public void deleteSkillById(int id) {
        skillMap.remove(id);
    }

    // REVU а если нет такого логина ?
    public User getUserByLogin(String login) {
        return users.get(login);
    }

    // REVU а если нет такого токена ?
    public User getUserByToken(UUID token) {
        return tokens.get(token);
    }

    // REVU и далее
    public User getUserById(int id) {
        return userMap.get(id);
    }

    public Skill getSkillById(int id) {
        return skillMap.get(id);
    }

    public EmployeeRequirement getRequirementById(int id) {
        return requirementMap.get(id);
    }

    public Vacancy getVacancyById(int id) {
        return vacancyMap.get(id);
    }

    public void clear() {
        users.clear();
        tokens.clear();
        userMap.clear();
        skillMap.clear();
        requirementMap.clear();
        vacancyMap.clear();
    }
}
