package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.request.AddRequirementDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.response.GetRequirementDtoResponse;
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
    Requirement requirementToRequirementDto(AddRequirementDtoRequest requirementDto);
    Vacancy getVacancy(GetVacancyDtoResponse vacancyDto);
    GetRequirementDtoResponse getEmployeeRequirement(Requirement requirement);
    GetVacancyDtoResponse getVacancy(Vacancy vacancy);
}
