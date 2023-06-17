package net.thumbtack.school.hiring.database;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.User;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentMultiAndBidiMap {

    public ConcurrentMultiAndBidiMap() {

    }
    private final Multimap<Skill, Employee> employeeBySkills = TreeMultimap.create(
            Comparator.comparing(Skill::getSkillName).thenComparingInt(Skill::getProfLevel),
            Comparator.comparing(User::getLastName));

    private final BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    private final ReentrantLock mutex = new ReentrantLock();

    public Multimap<Skill, Employee> getEmployeeBySkills() {
        return employeeBySkills;
    }

    public BidiMap<UUID, User> getUserByToken() {
        return userByToken;
    }

    public void put(Skill skill, Employee employee) {
        try {
            mutex.lock();
            employeeBySkills.put(skill, employee);
        }
        finally {
            mutex.unlock();
        }
    }

    public boolean remove(Skill skill, Employee employee) {
        try {
            mutex.lock();
            return employeeBySkills.remove(skill, employee);
        }
        finally {
            mutex.unlock();
        }
    }

    public Collection<Employee> values() {
        try {
            mutex.lock();
            return employeeBySkills.values();
        }
        finally {
            mutex.unlock();
        }
    }

    public UUID getKey(User user) {
        try {
            mutex.lock();
            return userByToken.getKey(user);
        }
        finally {
            mutex.unlock();
        }
    }

    public void put(UUID token, User user) {
        try {
            mutex.lock();
            userByToken.put(token, user);
        }
        finally {
            mutex.unlock();
        }
    }

    public User remove(UUID token) {
        try {
            mutex.lock();
            return userByToken.remove(token);
        }
        finally {
            mutex.unlock();
        }
    }

    public User get(UUID token) {
        try {
            mutex.lock();
            return userByToken.get(token);
        }
        finally {
            mutex.unlock();
        }
    }
}