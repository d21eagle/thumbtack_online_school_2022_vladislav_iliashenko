package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.request.AddEmployeeRequirementDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.response.GetEmployeeRequirementDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetEmployerDtoResponse;
import net.thumbtack.school.hiring.dto.response.GetVacancyDtoResponse;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper( EmployerMapper.class );
    Employer employerToEmployerDto(RegisterEmployerDtoRequest employer);
    GetEmployerDtoResponse getEmployerDto(Employer employer);
    Employer getEmployer(GetEmployerDtoResponse employerDto);
    Vacancy vacancyToVacancyDto(AddVacancyDtoRequest vacancyDto);
    EmployeeRequirement requirementToRequirementDto(AddEmployeeRequirementDtoRequest requirementDto);
    Vacancy getVacancy(GetVacancyDtoResponse vacancyDto);
    GetEmployeeRequirementDtoResponse getEmployeeRequirement(EmployeeRequirement requirement);
    GetVacancyDtoResponse getVacancy(Vacancy vacancy);
}
