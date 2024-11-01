package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerEntity saveCustomer(CustomerEntity customer)
    {
        return customerRepository.save(customer);
    }

    public List<CustomerEntity> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public CustomerEntity getCustomerByPetId(Long id)
    {
        return customerRepository.findByPetsId(id);
    }

    public CustomerEntity getCustomerById(Long id)
    {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if(optionalCustomer.isPresent())
        {
            return optionalCustomer.get();
        }
        return optionalCustomer.orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found"));

    }
}
