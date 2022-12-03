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
    private final List<Skill> skills = new ArrayList<>();

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // REVU упорядочите код
    // сначала регистрация, логин-логаут-уход, потом скиилы, вакансии и т.д.
    // машине это все равно, а читать будет легче

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.LOGIN_ALREADY_USED);
        }
    }

    public void addSkill(UUID token, Skill skill) throws ServerException {
        // REVU это надо было в сервисе сделать
        // а сюда передать Employee, а не токен
        // токен проверяется в сервисе и по нему находится его владелец
        // а дальше действует владелец, а не токен
        // и будет логично - Skill добавлется к Employee (а не к токену)
        // Вахтер : пропуск!
        // Вы : пожалуйста
        // Вахтер вставляет пропуск куда положено и находит владельца
        // Вахтер : Владислав Ильяшенко, проходите
        // дальше действует Владислав Ильяшенко, а не пропуск
        // пропуск вообще больше не нужен - до следующего входа
        // здесь и везде
        User user = getUserByToken(token);
        if (!(user instanceof Employee)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        // REVU а вот null тут не должно быть
        // не надо позволять Employee иметь skills == null
        // при его создании надо было skills = new ArrayList<>()
        // и будет логично - после регистрации у него пустой список скиллов, потом можно будет добавить
        // вообще, если где-то List, то лучше не допускать там null, а иметь пустой список
        // по пустому списку можно итерировать и вообще делать что угодно
        // а с null того и гляди получите NullPointerException
        if (((Employee) user).getSkills() == null || ((Employee) user).getSkills().isEmpty()) {
            // REVU не понял. Если у него их нет, дать ему все, что накопились ? Зачем ?
            ((Employee) user).setSkills(skills);
        }
        skills.add(skill);
    }

    public void deleteSkill(UUID token, Skill skill) throws ServerException {
        User user = getUserByToken(token);
        if (!(user instanceof Employee)) {
            throw new ServerException(ServerErrorCode.INVALID_USERTYPE);
        }
        // REVU см. выше
        if (((Employee) user).getSkills() == null || ((Employee) user).getSkills().isEmpty()) {
            throw new ServerException(ServerErrorCode.EMPTY_SKILLS);
        }
        skills.remove(skill);
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    public User getUserByToken(UUID token) {
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
        // REVU а skills очистить не надо ?
        // он вообще-то нужен ? Какой-то список всех скиллов всех работников. Зачем ?
    }
}
