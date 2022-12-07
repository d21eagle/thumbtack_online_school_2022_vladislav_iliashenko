package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.request.AddSkillDtoRequest;
import net.thumbtack.school.hiring.dto.response.GetAllSkillsDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetEmployeeDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetSkillDtoResponse;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;

import net.thumbtack.school.hiring.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );
    Employee employeeToEmployeeDto(RegisterEmployeeDtoRequest employee);
    Skill skillToSkillDto(AddSkillDtoRequest skill);
    GetEmployeeDtoResponse getEmployeeDto(Employee employee);
    Employee getEmployee(GetEmployeeDtoResponse employeeDto);
    GetSkillDtoResponse getSkillDto(Skill skill);
}
