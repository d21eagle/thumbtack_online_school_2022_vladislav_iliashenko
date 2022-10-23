package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class Skill {

    private String skillName;
    private int profLevel;

    public Skill(String skillName, int profLevel) {
        setSkillName(skillName);
        setProfLevel(profLevel);
    }

    private void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setProfLevel(int profLevel) {
        this.profLevel = profLevel;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getProfLevel() {
        return profLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return profLevel == skill.profLevel && Objects.equals(skillName, skill.skillName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillName, profLevel);
    }
}
