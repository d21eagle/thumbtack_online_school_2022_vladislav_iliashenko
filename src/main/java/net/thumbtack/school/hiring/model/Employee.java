package net.thumbtack.school.hiring.model;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    private List<Skill> skills = new ArrayList<>();
}
