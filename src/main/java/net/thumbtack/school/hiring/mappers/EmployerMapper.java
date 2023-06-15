package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import java.util.List;
import java.util.Set;

public interface EmployerMapper {

    @Insert("INSERT INTO employer(id, companyName, companyAddress) VALUES(#{employer.userId}, #{employer.companyName}, #{employer.companyAddress})")
    int insert(@Param("employer") User employer);

    @Select("SELECT * FROM employer JOIN user ON employer.id = user.id WHERE employer.id = #{employerId}")
    @Results({ @Result(property = "userId", column = "id") })
    Employer getEmployerById(@Param("employerId") int employerId);

    @Insert("INSERT INTO vacancy(position, salary, employer_id) " +
            "VALUES(#{vacancy.position}, #{vacancy.salary}, #{employer.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "vacancy.vacancyId")
    void addVacancy(@Param("vacancy") Vacancy vacancy, @Param("employer") Employer employer);

    @Select("SELECT id FROM vacancy WHERE position = #{vacancy.position} AND salary = #{vacancy.salary} AND employer_id = #{employer.userId}" )
    int getIdVacancyByInfo(@Param("vacancy")Vacancy vacancy, @Param("employer") Employer employer);

    @Insert("INSERT INTO vacancy_requirement(requirementName, profLevel, isNecessary, vacancy_id) " +
            "VALUES(#{vacancyRequirement.requirementName}, #{vacancyRequirement.profLevel}," +
            " #{vacancyRequirement.isNecessary}, #{vacancy.vacancyId})")
    @Options(useGeneratedKeys = true, keyProperty = "vacancyRequirement.requirementId")
    void addVacancyRequirement(@Param("vacancyRequirement") Requirement requirement, @Param("vacancy")Vacancy vacancy);

    @Select("SELECT id FROM vacancy_requirement WHERE requirementName = #{vacancyRequirement.requirementName} " +
            "AND profLevel = #{vacancyRequirement.profLevel} AND isNecessary = #{vacancyRequirement.isNecessary} AND vacancy_id = #{vacancy.vacancyId}" )
    int getIdVacancyRequirementByInfo(@Param("vacancyRequirement")Requirement requirement, @Param("vacancy")Vacancy vacancy);

    @Select("SELECT vacancy_id FROM vacancy_requirement WHERE requirementName = #{vacancyRequirement.requirementName} " +
            "AND profLevel = #{vacancyRequirement.profLevel} AND isNecessary = #{vacancyRequirement.isNecessary} ")
    int getIdVacancyByRequirement(@Param("vacancyRequirement")Requirement requirement);

    @Delete("DELETE FROM vacancy WHERE id = #{id}")
    void deleteVacancy(@Param("id") int id);

    @Delete("DELETE FROM vacancy_requirement WHERE id = #{id}")
    void deleteVacancyRequirement(@Param("id") int id);

    @Select("SELECT * FROM vacancy_requirement WHERE id = #{id}")
    Requirement getRequirementById(int id);

    @Select("SELECT * FROM vacancy WHERE id = #{id}")
    Vacancy getVacancyById(int id);

    @Select("SELECT * FROM vacancy")
    List<Vacancy> getAllVacancies();

    @Select("SELECT * FROM vacancy_requirement")
    List<Requirement> getAllRequirements();

    @Select("SELECT * FROM skill WHERE employee_id = #{employee.id}")
    Set<Skill> getSkillsByEmployee(Employee employee);

    @Select("SELECT * " +
            "FROM employee JOIN user ON employee.id = user.id JOIN skill ON employee.id = skill.employee_id " +
            "WHERE (skill.skillName = (SELECT requirementName FROM vacancy_requirement " +
            "WHERE vacancy_requirement.requirementName = #{vacancyRequirement.requirementName})) " +
            "AND (skill.profLevel >= " +
            "(SELECT profLevel FROM vacancy_requirement WHERE vacancy_requirement.profLevel = #{vacancyRequirement.profLevel} LIMIT 1))")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.EmployerMapper.getSkillsByEmployee",
                            fetchType = FetchType.LAZY))})
    List<Employee> getEmployeesByVacancyRequirement(Requirement requirement);
}
