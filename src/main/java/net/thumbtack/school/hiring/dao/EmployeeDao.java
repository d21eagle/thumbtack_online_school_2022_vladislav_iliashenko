package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dto.request.*;
import java.util.*;


public interface EmployeeDao {

    void insert(Employee employee) throws ServerException;
    UUID loginUser (User user) throws ServerException;
    void logoutUser (LogoutEmployeeDtoRequest logoutEmployeeDtoRequest) throws ServerException;
    User getUserByLogin(String login) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
    void clear();
}
