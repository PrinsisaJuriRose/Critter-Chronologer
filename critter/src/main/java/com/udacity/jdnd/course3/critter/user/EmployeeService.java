package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeEntity saveEmployee(EmployeeEntity employee)
    {
        return employeeRepository.save(employee);
    }

    public EmployeeEntity getEmployeeById(Long id)
    {
        Optional<EmployeeEntity> optionalEmployee= employeeRepository.findById(id);

        if(optionalEmployee.isPresent())
        {
            return optionalEmployee.get();
        }

        return optionalEmployee.orElseThrow(()-> new ResourceNotFoundException("Could not find employee with id: " + id));
    }

   List<EmployeeEntity> getEmployeesForService(Set<EmployeeSkill> skills, LocalDate date)
   {
       return employeeRepository.findAllByDaysAvailable(date.getDayOfWeek())
                            .stream().filter(employee -> employee.getSkills().containsAll(skills))
                            .collect(Collectors.toList());

   }
}
