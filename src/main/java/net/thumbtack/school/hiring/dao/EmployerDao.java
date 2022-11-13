package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;

public interface EmployerDao {

    void insert(Employer employee) throws ServerException;
}
