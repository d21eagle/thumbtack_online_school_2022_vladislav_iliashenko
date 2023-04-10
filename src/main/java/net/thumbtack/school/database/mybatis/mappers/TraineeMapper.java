package net.thumbtack.school.database.mybatis.mappers;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface TraineeMapper {
    @Insert("INSERT INTO ttschool.trainee (firstname, lastname, rating, idGroup) VALUES(#{trainee.firstName}, #{trainee.lastName}, #{trainee.rating}, #{group.id})")
    @Options(useGeneratedKeys = true, keyProperty = "trainee.id")
    Integer insert(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Select("SELECT* FROM ttschool.trainee WHERE id = #{id}")
    Trainee getById(int id);

    @Select("SELECT* FROM ttschool.trainee")
    List<Trainee> getAll();

    @Select("SELECT* FROM ttschool.trainee WHERE idGroup = #{group.id}")
    List<Trainee> getTraineesByGroup(Group group);

    @Update("UPDATE ttschool.trainee SET firstName = #{trainee.firstName}, lastName = #{trainee.lastName}, rating = #{trainee.rating} WHERE (id = #{trainee.id})")
    void update(@Param("trainee") Trainee trainee);

    @Select({"<script>",
            "SELECT * FROM trainee",
            "<where>",
            "<if test='firstName != null'> AND firstname like #{firstName} </if>",
            "<if test='lastName != null'> AND lastname like #{lastName} </if>",
            "<if test='rating != null'> AND rating = #{rating} </if>",
            "</where>",
            "</script>"})
    List<Trainee> getAllWithParams(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rating") Integer rating);

    @Delete("DELETE FROM ttschool.trainee")
    void deleteAll();

    @Insert({"<script>",
            "INSERT INTO ttschool.trainee (firstname, lastname, rating) VALUES",
            "<foreach item='item' collection='trainees' separator=','>",
            "( #{item.firstName}, #{item.lastName}, #{item.rating})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "trainees.id")
    void batchInsert(@Param("trainees") List<Trainee> trainees);

    @Delete("DELETE FROM ttschool.trainee WHERE id = #{trainee.id}")
    void delete(@Param("trainee") Trainee trainee);
}