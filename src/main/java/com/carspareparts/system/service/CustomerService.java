package com.carspareparts.system.service;

import com.carspareparts.system.domain.Customer;
import com.carspareparts.system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;

    public String save(Customer entity) {
        customerRepository.save(entity);
        return "Customer saved successfully";
    }
    public List<Customer> getAll() { return customerRepository.findAll(); }

    public Object getById(UUID id) {
        return customerRepository.findById(id)
                .map(customer -> (Object) customer)
                .orElse("The customer with that id does not exist");
    }

    public String update(UUID id, Customer newData) {
        return customerRepository.findById(id).map(existing -> {
            existing.setFirstName(newData.getFirstName());
            existing.setLastName(newData.getLastName());
            existing.setCustomerCode(newData.getCustomerCode());
            existing.setPhone(newData.getPhone());
            customerRepository.save(existing);
            return "Customer updated successfully";
        }).orElse("The customer with that id does not exist");
    }

    public String delete(UUID id) {
        if (!customerRepository.existsById(id)) return "The customer with that id does not exist";
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}