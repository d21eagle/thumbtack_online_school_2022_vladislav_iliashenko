package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import java.util.UUID;

public interface EmployerDao {
    void insert(Employer employer) throws ServerException;
    User getUserByToken(UUID token) throws ServerException;
}
