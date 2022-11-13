package net.thumbtack.school.hiring.dao;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;

public interface EmployeeDao {

    void insert(Employee employee) throws ServerException;
}
