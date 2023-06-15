package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.User;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

public interface UserMapper {

    @Insert("INSERT INTO user(email, login, password, lastName, middleName, firstName)" +
            "VALUES (#{user.email}, #{user.login}, #{user.password}, #{user.lastName}, #{user.middleName}, #{user.firstName})")
    @Options(useGeneratedKeys = true, keyProperty = "user.userId")
    Integer insert(@Param("user") User user);

    @Insert("INSERT INTO session(id, uuid) VALUES(#{user.userId}, #{uuid})")
    void loginUser (@Param("user") User user, @Param("uuid") String uuid);

    @Delete("DELETE FROM session WHERE uuid = #{uuid}")
    void logoutUser (@Param("uuid") String token);

    @Select("SELECT * FROM user WHERE (login = #{login})")
    @Results({ @Result(property = "userId", column = "id") })
    User getUserByLogin(String login);

    @Select("SELECT * FROM user WHERE id = (SELECT id FROM session WHERE uuid = #{uuid})")
    @Results({ @Result(property = "userId", column = "id") })
    User getUserByToken(@Param("uuid") String token);

    @Select("SELECT id FROM user WHERE (login = #{user.login})")
    Integer getIdByUser(@Param("user") User user);

    @Select("SELECT id FROM session WHERE (uuid = #{uuid})")
    Integer getIdByToken(@Param("uuid") String uuid);

    @Delete("DELETE FROM user")
    void clear();
}
