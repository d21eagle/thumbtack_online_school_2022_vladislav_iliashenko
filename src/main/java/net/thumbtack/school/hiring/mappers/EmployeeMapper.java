package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface EmployeeMapper {

    @Insert("INSERT INTO employee(id) VALUES(#{employee.userId})")
    int insert(@Param("employee") User employee);

    @Select("SELECT * FROM employee JOIN user ON employee.id = user.id WHERE employee.id = #{employeeId}")
    @Results({ @Result(property = "userId", column = "id") })
    Employee getEmployeeById(@Param("employeeId") int employeeId);

    @Insert("INSERT INTO skill(skillName, profLevel, employee_id) VALUES(#{skill.skillName}, #{skill.profLevel}, #{employee.userId})")
    @Options(useGeneratedKeys=true, keyProperty="skill.id", keyColumn="id")
    void addSkill(@Param("skill")Skill skill, @Param("employee") Employee employee);

    @Select("SELECT id FROM skill WHERE skillName = #{skill.skillName} AND profLevel = #{skill.profLevel} AND employee_id = #{employee.userId}" )
    int getIdSkillByInfo(@Param("skill")Skill skill, @Param("employee") Employee employee);

    @Delete("DELETE FROM skill WHERE id = #{id}")
    void deleteSkill(@Param("id") int id);

    @Select("SELECT * FROM skill WHERE id = #{id}")
    Skill getSkillById(@Param("id") int id);

    @Select("SELECT * FROM skill")
    List<Skill> getAllSkills();
}
