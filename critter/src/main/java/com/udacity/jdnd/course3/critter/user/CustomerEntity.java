package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerEntity extends PersonEntity{

    private String phoneNumber;
    private String notes;

    @OneToMany(targetEntity = PetEntity.class, fetch = FetchType.LAZY)
    private List<PetEntity> pets;

    public CustomerEntity(long id, List<PetEntity> pets, String notes, String phoneNumber, String name) {
        super(id, name);
        this.pets = pets;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
    }

    public CustomerEntity(){}

    public List<PetEntity> getPet() {
        return pets;
    }

    public void setPet(List<PetEntity> pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
