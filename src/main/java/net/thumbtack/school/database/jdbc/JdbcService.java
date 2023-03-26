package net.thumbtack.school.database.jdbc;
import net.thumbtack.school.database.model.*;
import java.sql.*;
import java.util.*;

public class JdbcService {
    //Добавляет Trainee в базу данных.
    public static void insertTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String insertTrainee = "INSERT INTO ttschool.trainee (id, firstname, lastname, rating) VALUES(?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertTrainee, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, trainee.getFirstName());
            statement.setString(3, trainee.getLastName());
            statement.setInt(4, trainee.getRating());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                trainee.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Изменяет ранее записанный Trainee в базе данных.
    public  static void updateTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String updateTrainee = "UPDATE ttschool.trainee SET `id` = ?,`firstname` = ?, `lastname` = ?, `rating`=? WHERE (`id` = ?)";

        try (PreparedStatement statement = connection.prepareStatement(updateTrainee, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, trainee.getId());
            statement.setString(2, trainee.getFirstName());
            statement.setString(3, trainee.getLastName());
            statement.setInt(4, trainee.getRating());
            statement.setInt(5, trainee.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает Trainee  из базы данных по его ID, используя метод получения “по именам полей”.
    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getTrainee = "SELECT* FROM trainee WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getTrainee)) {
            statement.setInt(1, traineeId);
            ResultSet result = statement.executeQuery();
            Trainee trainee = null;
            if (result.next()) {
                trainee = new Trainee(
                        result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getInt("rating")
                );
            }
            return trainee;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает Trainee  из базы данных по его ID, используя метод получения “по номерам полей”.
    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getTrainee = "SELECT* FROM trainee WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getTrainee)) {
            statement.setInt(1, traineeId);
            ResultSet result = statement.executeQuery();
            Trainee trainee = null;
            if (result.next()) {
                trainee = new Trainee(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4)
                );
            }
            return trainee;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает все Trainee  из базы данных, используя метод получения “по именам полей”.
    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getTrainees = "SELECT* FROM trainee";

        try (PreparedStatement statement = connection.prepareStatement(getTrainees)) {
            ResultSet result = statement.executeQuery();
            List<Trainee> trainees = new ArrayList<>();

            while (result.next()) {
                trainees.add(new Trainee(
                        result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getInt("rating"))
                );
            }
            return trainees;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает все Trainee  из базы данных, используя метод получения “по номерам полей”.
    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getTrainees = "SELECT* FROM trainee";

        try (PreparedStatement statement = connection.prepareStatement(getTrainees)) {
            ResultSet result = statement.executeQuery();
            List<Trainee> trainees = new ArrayList<>();
            while (result.next()) {
                trainees.add(new Trainee(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4))
                );
            }
            return trainees;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Удаляет Trainee из базы данных.
    public static void deleteTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String deleteTrainee = "DELETE FROM trainee WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteTrainee)) {
            statement.setInt(1, trainee.getId());
            statement.executeUpdate();
        }
    }

    //Удаляет все Trainee из базы данных.
    public static void deleteTrainees() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String deleteTrainees = "DELETE FROM trainee";
        try (PreparedStatement statement = connection.prepareStatement(deleteTrainees)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Добавляет Subject в базу данных.
    public static void insertSubject(Subject subject) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String insertSubject = "INSERT INTO ttschool.subject (id, name) VALUES(NULL,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSubject, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subject.getName());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                subject.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает Subject  из базы данных по его ID, используя метод получения “по именам полей”.
    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getSubject = "SELECT* FROM subject WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getSubject)) {
            statement.setInt(1, subjectId);
            ResultSet result = statement.executeQuery();
            Subject subject = null;
            if (result.next()) {
                subject = new Subject(result.getInt("id"), result.getString("name"));
            }
            return subject;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает Subject  из базы данных по его ID, используя метод получения “по номерам полей”.
    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getSubject = "SELECT* FROM subject WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getSubject)) {
            statement.setInt(1, subjectId);
            ResultSet result = statement.executeQuery();
            Subject subject = null;
            while (result.next()) {
                subject = new Subject(result.getInt(1), result.getString(2));
            }
            return subject;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Удаляет все Subject из базы данных.
    public static void deleteSubjects() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String deleteSubjects = "DELETE FROM subject";

        try (PreparedStatement statement = connection.prepareStatement(deleteSubjects)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Добавляет School в базу данных.
    public static void insertSchool(School school) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String insertSchool = "INSERT INTO ttschool.school (id, name, year) VALUES(NULL,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSchool, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, school.getName());
            statement.setInt(2, school.getYear());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                school.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает School  из базы данных по ее ID, используя метод получения “по именам полей”.
    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getSchool = "SELECT* FROM school WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getSchool)) {
            statement.setInt(1, schoolId);
            ResultSet result = statement.executeQuery();
            School school = null;
            while (result.next()) {
                school = new School(result.getInt("id"), result.getString("name"), result.getInt("year"));
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает School  из базы данных по ее ID, используя метод получения “по номерам полей”.
    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String getSchool = "SELECT* FROM school WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getSchool)) {
            statement.setInt(1, schoolId);
            ResultSet result = statement.executeQuery();
            School school = null;
            while (result.next()) {
                school = new School(result.getInt(1), result.getString(2), result.getInt(3));
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Удаляет все School из базы данных.
    public static void deleteSchools() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String deleteSchools = "DELETE FROM school";

        try (PreparedStatement statement = connection.prepareStatement(deleteSchools)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Добавляет Group в базу данных, устанавливая ее принадлежность к школе School.
    public static void insertGroup(School school, Group group) throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String insertGroup = "INSERT INTO ttschool.`group` (id, name, room, idSchool) VALUES(NULL,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertGroup, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            statement.setString(2, group.getRoom());
            statement.setInt(3, school.getId());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                group.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    //Получает School по ее ID вместе со всеми ее Group из базы данных.
    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String getSchool = "SELECT * FROM `school` JOIN `group` ON `group`.`idSchool` = ?";

        try (PreparedStatement statement = connection.prepareStatement(getSchool)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            School school = null;
            if (result.next()) {
                school = new School(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3)
                );
                school.addGroup(new Group(
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6))
                );
            }
            while (result.next()) {
                if (school != null) {
                    school.addGroup(new Group(
                            result.getInt(4),
                            result.getString(5),
                            result.getString(6))
                    );
                }
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }

    }

    //Получает список всех School вместе со всеми их Group из базы данных.
    public static List<School> getSchoolsWithGroups() throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String getSchools = "SELECT * FROM `school` JOIN `group` ON `school`.`id` = `group`.`idSchool`";

        try (PreparedStatement statement = connection.prepareStatement(getSchools)) {
            List<School> schools = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            School school = null;
            int prevId = 0;
            if (result.next()) {
                school = new School(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3)
                );
                school.addGroup(new Group(
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6))
                );
                prevId = result.getInt(1);
            }
            while (result.next()) {
                if (result.getInt(1) != prevId) {
                    schools.add(school);
                    prevId = result.getInt(1);
                    school = new School(
                            result.getInt(1),
                            result.getString(2),
                            result.getInt(3)
                    );
                }
                if (school != null) {
                    school.addGroup(new Group(
                            result.getInt(4),
                            result.getString(5),
                            result.getString(6))
                    );
                }
            }
            schools.add(school);
            return schools;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
