package net.thumbtack.school.database.model;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class School {
    private int id;
    private String name;
    private int year;
    private List<Group> groups;

    public School(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.groups = new ArrayList<>();
    }

    public School(String name, int year) {
        this.id = 0;
        this.name = name;
        this.year = year;
        this.groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return getId() == school.getId() && getYear() == school.getYear() && Objects.equals(getName(), school.getName()) && Objects.equals(getGroups(), school.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear(), getGroups());
    }
}
