package net.thumbtack.school.database.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Subject {
    private int id;
    private String name;

    public Subject(String name) {
        this.id = 0;
        this.name = name;
    }
}
