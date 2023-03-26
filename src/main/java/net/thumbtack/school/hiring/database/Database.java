package net.thumbtack.school.hiring.database;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.*;
import java.util.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Database {
    private static Database instance;
    private final Map<String, User> userByLogin = new HashMap<>();
    private final BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();
    private final Map<Integer, User> userById = new HashMap<>();
    private final Map<Integer, Skill> skillById = new HashMap<>();
    private final Map<Integer, Requirement> requirementById = new HashMap<>();
    private final Map<Integer, Vacancy> vacancyById = new HashMap<>();
    private final Multimap<Skill, Employee> employeeBySkills = TreeMultimap.create(
            Comparator.comparing(Skill::getSkillName).thenComparingInt(Skill::getProfLevel),
            Comparator.comparing(User::getLastName));
    private int nextUserId = 1;
    private int nextSkillId = 1;
    private int nextVacancyId = 1;
    private int nextRequirementId = 1;

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
        user.setUserId(nextUserId);
        userById.put(nextUserId, user);
        return nextUserId++;
    }

    public UUID loginUser(User user) {
        UUID uuid = userByToken.getKey(user);
        if (uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public void logoutUser(UUID token) throws ServerException {
        if (userByToken.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    public User getUserByLogin(String login) {
        return userByLogin.get(login);
    }

    public User getUserByToken(UUID token) {
        return userByToken.get(token);
    }

    public User getUserById(int id) {
        return userById.get(id);
    }

    public int addSkill(Skill skill, Employee employee) {
        employee.getSkills().forEach(skill1 -> employeeBySkills.remove(skill1, employee));
        skill.setSkillId(nextSkillId);
        employee.getSkills().forEach(skill1 -> employeeBySkills.put(skill1, employee));
        skillById.put(nextSkillId, skill);
        return nextSkillId++;
    }

    public int addVacancy(Vacancy vacancy) {
        vacancy.setVacancyId(nextVacancyId);
        vacancyById.put(nextVacancyId, vacancy);
        return nextVacancyId++;
    }

    public int addVacancyRequirement(Requirement requirement) {
        requirement.setRequirementId(nextRequirementId);
        requirementById.put(nextRequirementId, requirement);
        return nextRequirementId++;
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

//    public Set<Employee> getEmployeesByRequirements(List<Requirement> requirements) {
//        Set<Employee> shortlist = new HashSet<>();
//        boolean flag = true;
//        for (Skill skill : employeeBySkills.keySet()) {
//            Collection<Employee> employees = employeeBySkills.get(skill);
//            if (flag) {
//                shortlist.addAll(employees);
//            }
//            else {
//                for (Requirement requirement : requirements) {
//                    List<Collection<Employee>> collection = new ArrayList<>(((TreeMultimap) employeeBySkills).asMap().subMap(
//                            new Skill(requirement.getRequirementName(), requirement.getProfLevel()),
//                            new Skill(requirement.getRequirementName(), 6)).values());
//                    shortlist.retainAll(collection.get(0));
//                }
//            }
//            flag = false;
//        }
//        return shortlist;
//    }

    public Set<Employee> getEmployeesByRequirements(List<Requirement> requirements) {
        Set<Employee> shortlist = new HashSet<>();
        boolean flag = true;

        for (Requirement requirement : requirements) {
            Collection<Employee> employees = employeeBySkills.values();
            if (flag) { shortlist.addAll(employees); }
            else {
                Collection<Employee> collection = new ArrayList<>(((TreeMultimap) employeeBySkills).asMap().subMap(
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
        userByToken.clear();
        userById.clear();
        skillById.clear();
        requirementById.clear();
        vacancyById.clear();
        employeeBySkills.clear();
        nextUserId = 1;
        nextSkillId = 1;
        nextVacancyId = 1;
        nextRequirementId = 1;
    }
}
