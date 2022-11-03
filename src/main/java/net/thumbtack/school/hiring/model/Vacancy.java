package net.thumbtack.school.hiring.model;
import java.util.*;

public class Vacancy {

    // REVU post - это почта, а здесь скорее position
    private String post;
    private int salary;

    private List<EmployeeRequirement> requirementsList;

    public Vacancy(String post, int salary, List<EmployeeRequirement> requirementsList) {
        setPost(post);
        setSalary(salary);
        setRequirementsList(requirementsList);
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPost() {
        return post;
    }

    public int getSalary() {
        return salary;
    }

    public void setRequirementsList(List<EmployeeRequirement> requirementsList) {
        this.requirementsList = requirementsList;
    }

    public List<EmployeeRequirement> getRequirementsList() {
        return requirementsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary && Objects.equals(post, vacancy.post) && Objects.equals(requirementsList, vacancy.requirementsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, salary, requirementsList);
    }
}
