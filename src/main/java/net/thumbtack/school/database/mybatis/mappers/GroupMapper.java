package net.thumbtack.school.database.mybatis.mappers;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import java.util.List;

public interface GroupMapper {
    @Insert("INSERT INTO ttschool.group (name, room, idSchool) VALUES(#{group.name},#{group.room},#{school.id})")
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    Integer insert(@Param("school") School school, @Param("group") Group group);

    @Update("UPDATE ttschool.group SET name = #{group.name}, room = #{group.room} WHERE (id = #{group.id})")
    void update(@Param("group") Group group);

    @Select("SELECT * from ttschool.group")
    List<Group> getAll();

    @Select("SELECT * from ttschool.group WHERE (idSchool = #{school.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getTraineesByGroup",
                            fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getSubjectsByGroup",
                            fetchType = FetchType.LAZY))})
    List<Group> getBySchool(School school);

    @Select("SELECT * from ttschool.group WHERE (idSchool = #{school.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getTraineesByGroup",
                            fetchType = FetchType.EAGER)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getSubjectsByGroup",
                            fetchType = FetchType.EAGER))})
    List<Group> getBySchoolUsingJoin(School school);

    @Delete("DELETE FROM ttschool.group WHERE (id = #{group.id})")
    void delete(@Param("group") Group group);

    @Update("UPDATE ttschool.trainee SET idGroup = NULL WHERE (id = #{trainee.id})")
    void deleteTraineeFromGroup(@Param("trainee") Trainee trainee);

    @Insert("INSERT INTO ttschool.timetable (id_group, id_subject) VALUES(#{group.id}, #{subject.id})")
    void addSubjectToGroup(@Param("group") Group group, @Param("subject") Subject subject);

    @Update("UPDATE ttschool.trainee SET idGroup = #{group.id} WHERE (id = #{trainee.id})")
    void moveTraineeToGroup(@Param("group") Group group, @Param("trainee") Trainee trainee);
}