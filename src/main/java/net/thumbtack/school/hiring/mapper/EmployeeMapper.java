package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.response.GetEmployeeByTokenDtoResponse;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );
    Employee employeeToEmployeeDto(RegisterEmployeeDtoRequest employee);
    GetEmployeeByTokenDtoResponse getEmployeeByToken(Employee employee);
}
