package net.thumbtack.school.hiring.model;
import java.util.*;
import java.util.Objects;

public class Employee extends User {

    private List<Skill> skills;

    public Employee(String email,
                    String lastname,
                    String firstName,
                    String middleName,
                    String login,
                    String password,
                    List<Skill> skills) {
        super(email, lastname, firstName, middleName, login, password);
        setSkills(skills);
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(skills, employee.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), skills);
    }
}
