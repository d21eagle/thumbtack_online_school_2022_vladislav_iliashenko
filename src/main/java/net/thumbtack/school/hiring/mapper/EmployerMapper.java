package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.request.AddRequirementDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.RequirementDtoRequest;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper( EmployerMapper.class );
    Employer employerToEmployerDto(RegisterEmployerDtoRequest employer);
    GetEmployerDtoResponse getEmployerDto(Employer employer);
    Employer getEmployer(GetEmployerDtoResponse employerDto);
    Vacancy vacancyToVacancyDto(AddVacancyDtoRequest vacancyDto);
    Requirement requirementToRequirementDto(AddRequirementDtoRequest requirementDto);
    Vacancy getVacancyDto(GetVacancyDtoResponse vacancyDto);
    GetRequirementDtoResponse getVacancyRequirementDto(Requirement requirement);
    GetVacancyDtoResponse getVacancyDto(Vacancy vacancy);
    List<RequirementDtoRequest> getRequirementRequest(List<GetRequirementDtoResponse> responseList);
}
