package net.thumbtack.school.database.model;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Group {
    private int id;
    private String name;
    private String room;
    private List<Trainee> trainees;
    private List<Subject> subjects;

    public Group(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.trainees = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public Group(String name, String room) {
        this.id = 0;
        this.name = name;
        this.room = room;
        this.trainees = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public void addTrainee(Trainee trainee) {
        trainees.add(trainee);
    }

    public void removeTrainee(Trainee trainee) {
        trainees.remove(trainee);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
}
