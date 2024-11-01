package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        ScheduleEntity scheduleEntity = scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(scheduleEntity);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> scheduleEntities = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(ScheduleEntity schedule: scheduleEntities)
        {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleEntity> scheduleEntities =  scheduleService.getScheduleByPetId(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(ScheduleEntity schedule: scheduleEntities)
        {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleEntity> scheduleEntities =  scheduleService.getScheduleByEmployeeId(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(ScheduleEntity schedule: scheduleEntities)
        {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleEntity> scheduleEntities =  scheduleService.getScheduleByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(ScheduleEntity schedule: scheduleEntities)
        {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(ScheduleEntity schedule) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // setting the rest (pets, employees) of properties for schedulePTO
        List<PetEntity> pets = schedule.getPet();
        List<Long> petId = new ArrayList<>();
        for (PetEntity pet : pets) {
            petId.add(pet.getId());
        }
        scheduleDTO.setPetIds(petId);
        List<EmployeeEntity> employees = schedule.getEmployee();
        List<Long> employeeId = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            employeeId.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeId);
        return scheduleDTO;

    }

    private ScheduleEntity convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        ModelMapper modelMapper = new ModelMapper();
        ScheduleEntity schedule = modelMapper.map(scheduleDTO, ScheduleEntity.class);

        // setting the rest (pets, employees) of properties for schedule
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Optional<EmployeeEntity> optionalEmployee = Optional.ofNullable(employeeService.getEmployeeById(employeeId));
            if (optionalEmployee.isPresent()) {
                employeeEntities.add(optionalEmployee.get());
            } else {
                throw new ResourceNotFoundException("Could not find employee with id: " + employeeId);
            }
        }
        schedule.setEmployee(employeeEntities);
        List<PetEntity>petEntities = new ArrayList<>();
        for (Long petId : scheduleDTO.getPetIds()) {
            Optional<PetEntity> optionalPet = Optional.ofNullable(petService.getPetById(petId));
            if (optionalPet.isPresent()) {
                petEntities.add(optionalPet.get());
            } else {
                throw new ResourceNotFoundException("Could not find pet with id: " + petId);
            }
        }
        schedule.setPet(petEntities);
        return schedule;
    }

}
