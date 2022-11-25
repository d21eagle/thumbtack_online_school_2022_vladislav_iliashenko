package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public interface EmployerDao {

    void insert(Employer employer) throws ServerException;
    UUID loginUser (User user) throws ServerException;
    void logoutUser (LogoutEmployerDtoRequest logoutEmployerDtoRequest) throws ServerException;
    User getUserByLogin(String login) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
    void clear();
}
