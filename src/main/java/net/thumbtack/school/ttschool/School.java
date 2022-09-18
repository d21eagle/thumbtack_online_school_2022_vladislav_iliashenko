package net.thumbtack.school.ttschool;

import java.util.*;

public class School {

    private String name;
    private int year;
    private Set<Group> groups;

    public School(String name, int year) throws TrainingException {
        checkName(name);
        this.name = name;
        this.year = year;
        this.groups = new HashSet<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws TrainingException {
        checkName(name);
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws TrainingException {
        for (Group group1: groups) {
            if (group.getName().equals(group1.getName())) {
                throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
            }
        }
        this.groups.add(group);
    }

    public void removeGroup(Group group) throws TrainingException {
        if (!groups.remove(group)) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public void removeGroup(String name) throws TrainingException {
        int index = 0;
        for (Group group: groups) {
            if (group.getName().equals(name)){
                groups.remove(group);
                index = 1;
            }
        }
        if (index == 0) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public boolean containsGroup(Group group) {
        return groups.contains(group);
    }

    public static void checkName(String name) throws TrainingException {
        if(name == null || name.equals(""))
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year && Objects.equals(name, school.name) && Objects.equals(groups, school.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groups);
    }
}
