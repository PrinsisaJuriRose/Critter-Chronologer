package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class EmployeeEntity extends PersonEntity{

    @ElementCollection
    private Set<EmployeeSkill> skills;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;


    public EmployeeEntity(long id, Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills, String name) {
        super(id, name);
        this.daysAvailable = daysAvailable;
        this.skills = skills;
    }

    public EmployeeEntity(){}


    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}
