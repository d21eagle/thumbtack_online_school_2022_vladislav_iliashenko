package net.thumbtack.school.database.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Trainee {
    private int id;
    private String firstName;
    private String lastName;
    private int rating;

    public Trainee(String firstName, String lastName, int rating) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
    }
}
