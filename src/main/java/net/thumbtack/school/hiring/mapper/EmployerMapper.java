package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.response.GetEmployeeRequirementDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetEmployerDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetVacancyDtoResponse;
import net.thumbtack.school.hiring.model.EmployeeRequirement;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;

import net.thumbtack.school.hiring.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper( EmployerMapper.class );
    Employer employerToEmployerDto(RegisterEmployerDtoRequest employer);
    GetEmployerDtoResponse getEmployer(Employer employer);
    GetEmployeeRequirementDtoResponse getEmployeeRequirement(EmployeeRequirement requirement);
    GetVacancyDtoResponse getVacancy(Vacancy vacancy);
}
