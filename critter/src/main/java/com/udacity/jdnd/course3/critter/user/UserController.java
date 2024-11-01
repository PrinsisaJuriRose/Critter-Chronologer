package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerEntity customerEntity = convertCustomerDTOToCustomer(customerDTO);
        return convertCustomerToCustomerDTO(customerService.saveCustomer(customerEntity));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
       List<CustomerEntity> customerEntities = customerService.getAllCustomers();

       for(CustomerEntity customerEntity: customerEntities)
       {
           customerDTOS.add(convertCustomerToCustomerDTO(customerEntity));
       }

       return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        PetEntity pet = petService.getPetById(petId);
        if(pet.getCustomer() != null){
            return convertCustomerToCustomerDTO(pet.getCustomer());
        }
        else throw new UnsupportedOperationException("Apparently the Pet doesn't have an Owner yet!");
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
         EmployeeEntity employee = employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
       return convertEmployeeToEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        EmployeeEntity employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
           List<EmployeeEntity>  employeeEntities= employeeService.getEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate());
           List<EmployeeDTO> employeeDTOS = new ArrayList<>();

           for(EmployeeEntity employee: employeeEntities)
           {
               employeeDTOS.add(convertEmployeeToEmployeeDTO(employee));
           }
           return employeeDTOS;
    }

    private CustomerDTO convertCustomerToCustomerDTO(CustomerEntity customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<PetEntity> pets = customer.getPet();

        if (pets != null) {
            List<Long> petIds = new ArrayList<>();

            for (PetEntity pet : pets) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private CustomerEntity convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        ModelMapper modelMapper = new ModelMapper();
        CustomerEntity customer = modelMapper.map(customerDTO, CustomerEntity.class);
        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null) {
            List<PetEntity> pets = new ArrayList<PetEntity>();

            for (Long petId : petIds) {
                pets.add(petService.getPetById(petId));
            }
            customer.setPet(pets);
        }
        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(EmployeeEntity employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private EmployeeEntity convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employee = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return employee;
    }

}
