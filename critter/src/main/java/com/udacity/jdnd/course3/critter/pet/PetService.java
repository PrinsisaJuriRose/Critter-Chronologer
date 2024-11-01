package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    public PetEntity savePet(PetEntity pet)
    {
       return petRepository.save(pet);
    }

    public PetEntity getPetById(Long id)
    {
        Optional<PetEntity>optionalPet =  petRepository.findById(id);
        if (optionalPet.isPresent()) {
            return optionalPet.get();
        }
        return optionalPet.orElseThrow(() -> new ResourceNotFoundException("Pet with id: " + id + " not found"));

    }

    public List<PetEntity> getPetByOwnerId(Long id)
    {
       return petRepository.findPetEntitiesByCustomerId(id);
    }

    public List<PetEntity> getAllPets()
    {
        return petRepository.findAll();
    }
}
