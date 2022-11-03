package net.thumbtack.school.hiring.dto.request;
import java.util.*;
import net.thumbtack.school.hiring.model.Skill;

public class RegisterEmployeeDtoRequest extends RegisterUserDtoRequest {

    private List<Skill> skills;
    public RegisterEmployeeDtoRequest(String email,
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
        RegisterEmployeeDtoRequest that = (RegisterEmployeeDtoRequest) o;
        return Objects.equals(skills, that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), skills);
    }
}
