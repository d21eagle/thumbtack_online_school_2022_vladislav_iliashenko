package net.thumbtack.school.database.mybatis.mappers;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Subject;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface SubjectMapper {
    @Insert("INSERT INTO ttschool.subject (name) VALUES(#{subject.name})ON DUPLICATE KEY UPDATE subject.name = #{subject.name}")
    @Options(useGeneratedKeys = true, keyProperty = "subject.id")
    Integer insert(@Param("subject") Subject subject);

    @Select("SELECT* FROM ttschool.subject WHERE id = #{id}")
    Subject getById(int id);

    @Select("SELECT* FROM ttschool.subject")
    List<Subject> getAll();

    @Update("UPDATE ttschool.subject SET name = #{subject.name} WHERE (id = #{subject.id})")
    void update(@Param("subject") Subject subject);

    @Delete("DELETE FROM ttschool.subject WHERE (id = #{subject.id})")
    void delete(@Param("subject") Subject subject);

    @Select(" SELECT `subject`.`id`,  `subject`.`name` FROM `ttschool`.`subject` JOIN `timetable` ON `timetable`.`id_subject` =`subject`.`id` JOIN `group` ON group.id = `timetable`.`id_group` WHERE `timetable`.`id_group` = #{group.id}")
    List<Subject> getSubjectsByGroup(Group group);

    @Delete("DELETE FROM ttschool.subject")
    void deleteAll();
}