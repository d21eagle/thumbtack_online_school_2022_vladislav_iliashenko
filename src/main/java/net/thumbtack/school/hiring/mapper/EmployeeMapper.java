package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.request.AddSkillDtoRequest;
import net.thumbtack.school.hiring.dto.request.DeleteSkillDtoRequest;
import net.thumbtack.school.hiring.dto.response.GetEmployeeDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetSkillDtoResponse;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;

import net.thumbtack.school.hiring.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );
    Employee employeeToEmployeeDto(RegisterEmployeeDtoRequest employee);
    Skill skillToSkillDto(AddSkillDtoRequest skill);
    GetEmployeeDtoResponse getEmployee(Employee employee);
    GetSkillDtoResponse getSkill(Skill skill);
}
