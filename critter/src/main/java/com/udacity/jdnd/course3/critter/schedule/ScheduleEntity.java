package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(targetEntity = EmployeeEntity.class, fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;
    @ManyToMany(targetEntity = PetEntity.class, fetch = FetchType.LAZY)
    private List<PetEntity> pets;
    private LocalDate date;
    @ElementCollection
    private Set<EmployeeSkill> activities;

    public ScheduleEntity(long id, Set<EmployeeSkill> activities, LocalDate date, List<PetEntity> pets, List<EmployeeEntity> employees) {
        this.id = id;
        this.activities = activities;
        this.date = date;
        this.pets = pets;
        this.employees = employees;
    }

    public ScheduleEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<EmployeeEntity> getEmployee() {
        return employees;
    }

    public void setEmployee(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public List<PetEntity> getPet() {
        return pets;
    }

    public void setPet(List<PetEntity> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
