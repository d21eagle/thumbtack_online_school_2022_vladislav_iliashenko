package net.thumbtack.school.hiring.service;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );
    Employee employeeToEmployeeDto(RegisterEmployeeDtoRequest employee);
}
