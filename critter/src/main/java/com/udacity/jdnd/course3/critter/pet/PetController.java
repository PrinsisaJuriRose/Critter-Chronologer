package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO){
        PetEntity pet = convertPetDTOToPet(petDTO);
        PetEntity newPet = petService.savePet(pet);
        List<PetEntity>petEntities = new ArrayList<>();
        petEntities.add(newPet);
        //save pet in the customer
        CustomerEntity customer = customerService.getCustomerById(petDTO.getOwnerId());
        customer.setPet(petEntities);
        customerService.saveCustomer(customer);

        return convertPetToPetDTO(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
       return convertPetToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetEntity> pets = petService.getAllPets();
        List<PetDTO>petsDTO = new ArrayList<>();
        for(PetEntity pet:pets)
        {
            petsDTO.add(convertPetToPetDTO(pet));
        }
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
       List<PetEntity> pets = petService.getPetByOwnerId(ownerId);
       List<PetDTO>petsDTO = new ArrayList<>();
       for(PetEntity pet:pets)
       {
           petsDTO.add(convertPetToPetDTO(pet));
       }
       return petsDTO;
    }

    private PetDTO convertPetToPetDTO(PetEntity pet){
        PetDTO petDTO = new PetDTO();
        // To make copyProperties work correctly, properties of the DTO and entity object should match in name
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private PetEntity convertPetDTOToPet(PetDTO petDTO){
        ModelMapper modelMapper = new ModelMapper();
        PetEntity pet = modelMapper.map(petDTO, PetEntity.class);
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));
        return pet;
    }
}
