package net.thumbtack.school.hiring.database;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Database {
    private static Database instance;
    private final Map<String, User> userByLogin = new ConcurrentHashMap<>();
    private final Map<Integer, User> userById = new ConcurrentHashMap<>();
    private final Map<Integer, Skill> skillById = new ConcurrentHashMap<>();
    private final Map<Integer, Requirement> requirementById = new ConcurrentHashMap<>();
    private final Map<Integer, Vacancy> vacancyById = new ConcurrentHashMap<>();
    private final AtomicInteger nextUserId = new AtomicInteger(1);
    private final AtomicInteger nextSkillId = new AtomicInteger(1);
    private final AtomicInteger nextVacancyId = new AtomicInteger(1);
    private final AtomicInteger nextRequirementId = new AtomicInteger(1);
    private final ConcurrentMultiAndBidiMap concurrentMultiAndBidiMap = new ConcurrentMultiAndBidiMap();



    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public int insert(User user) throws ServerException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.LOGIN_ALREADY_USED);
        }
        user.setUserId(nextUserId.get());
        userById.put(nextUserId.get(), user);
        return nextUserId.incrementAndGet();
    }

    public UUID loginUser(User user) {
        UUID uuid = concurrentMultiAndBidiMap.getKey(user);
        if (uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        concurrentMultiAndBidiMap.put(token, user);
        return token;
    }

    public void logoutUser(UUID token) throws ServerException {
        if (concurrentMultiAndBidiMap.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    public User getUserByLogin(String login) {
        return userByLogin.get(login);
    }

    public User getUserByToken(UUID token) {
            return concurrentMultiAndBidiMap.get(token);
    }

    public int addSkill(Skill skill, Employee employee) {
        employee.getSkills().forEach(skill1 -> concurrentMultiAndBidiMap.remove(skill1, employee));
        skill.setId(nextSkillId.get());
        employee.getSkills().forEach(skill1 -> concurrentMultiAndBidiMap.put(skill1, employee));
        skillById.put(nextSkillId.get(), skill);
        return nextSkillId.getAndIncrement();
    }

    public int addVacancy(Vacancy vacancy) {
        vacancy.setVacancyId(nextVacancyId.get());
        vacancyById.put(nextVacancyId.get(), vacancy);
        return nextVacancyId.getAndIncrement();
    }

    public int addVacancyRequirement(Requirement requirement) {
        requirement.setRequirementId(nextRequirementId.get());
        requirementById.put(nextRequirementId.get(), requirement);
        return nextRequirementId.getAndIncrement();
    }

    public void deleteSkill(int id) throws ServerException {
        if (skillById.remove(id) == null) {
            throw new ServerException(ServerErrorCode.ID_NOT_EXIST);
        }
    }

    public void deleteVacancy(int id) throws ServerException {
        if (vacancyById.remove(id) == null) {
            throw new ServerException(ServerErrorCode.ID_NOT_EXIST);
        }
    }

    public void deleteVacancyRequirement(int id) throws ServerException {
        if (requirementById.remove(id) == null) {
            throw new ServerException(ServerErrorCode.ID_NOT_EXIST);
        }
    }

    public Skill getSkillById(int id) {
        return skillById.get(id);
    }

    public Requirement getRequirementById(int id) {
        return requirementById.get(id);
    }

    public Vacancy getVacancyById(int id) {
        return vacancyById.get(id);
    }

    public List<Skill> getAllSkills() {
        return new ArrayList<>(skillById.values());
    }

    public List<Vacancy> getAllVacancies() {
        return new ArrayList<>(vacancyById.values());
    }

    public List<Requirement> getAllRequirements() {
        return new ArrayList<>(requirementById.values());
    }

    public Set<Employee> getEmployeesByRequirements(List<Requirement> requirements) {
        Set<Employee> shortlist = new HashSet<>();
        boolean flag = true;

        for (Requirement requirement : requirements) {
            Collection<Employee> employees = concurrentMultiAndBidiMap.values();
            if (flag) {
                shortlist.addAll(employees);
            } else {
                Collection<Employee> collection = new ArrayList<>
                        (((TreeMultimap) concurrentMultiAndBidiMap.getEmployeeBySkills()).asMap().subMap(
                        new Skill(requirement.getRequirementName(), requirement.getProfLevel()),
                        new Skill(requirement.getRequirementName(), 6)).values());
                shortlist.retainAll(collection);
            }
            flag = false;
        }
        return shortlist;
    }

    public void clear() {
        userByLogin.clear();
        userById.clear();
        skillById.clear();
        requirementById.clear();
        vacancyById.clear();
        nextUserId.set(1);
        nextSkillId.set(1);
        nextVacancyId.set(1);
        nextRequirementId.set(1);
        concurrentMultiAndBidiMap.getUserByToken().clear();
        concurrentMultiAndBidiMap.getEmployeeBySkills().clear();
    }
}
