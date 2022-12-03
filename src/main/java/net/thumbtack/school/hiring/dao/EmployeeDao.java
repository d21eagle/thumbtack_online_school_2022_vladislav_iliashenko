package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.*;

public interface EmployeeDao {
    void insert(Employee employee) throws ServerException;
    void addSkill(UUID token, Skill skill) throws ServerException;
    void deleteSkill(UUID token, Skill skill) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
}
