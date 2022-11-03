package net.thumbtack.school.hiring.model;
import lombok.*;

import java.util.*;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Employee extends User {

    private List<Skill> skills;

    public Employee(String email, String lastname, String firstName, String middleName, String login, String password, List<Skill> skills) {
        super(email, lastname, firstName, middleName, login, password);
        setSkills(skills);
    }
}
