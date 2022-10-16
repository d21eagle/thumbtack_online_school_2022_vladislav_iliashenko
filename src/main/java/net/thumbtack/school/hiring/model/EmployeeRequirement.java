package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class EmployeeRequirement {

    private String requirementName;
    private int profLevel;
    private boolean isNecessary;

    public EmployeeRequirement(String requirementName, int profLevel, boolean isNecessary) {
        setRequirementName(requirementName);
        setProfLevel(profLevel);
        setNecessary(isNecessary);
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public void setProfLevel(int profLevel) {
        this.profLevel = profLevel;
    }

    public void setNecessary(boolean isNecessary) {
        this.isNecessary = isNecessary;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public int getProfLevel() {
        return profLevel;
    }

    public boolean getNecessary() {
        return isNecessary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRequirement that = (EmployeeRequirement) o;
        return profLevel == that.profLevel && isNecessary == that.isNecessary && Objects.equals(requirementName, that.requirementName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirementName, profLevel, isNecessary);
    }
}
