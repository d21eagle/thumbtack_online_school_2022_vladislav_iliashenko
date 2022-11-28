package net.thumbtack.school.hiring.mapper;
import net.thumbtack.school.hiring.dto.response.GetEmployerByTokenDtoResponse;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployerMapper {

    EmployerMapper INSTANCE = Mappers.getMapper( EmployerMapper.class );
    Employer employerToEmployerDto(RegisterEmployerDtoRequest employer);
    GetEmployerByTokenDtoResponse getEmployerByToken(Employer employer);
}
