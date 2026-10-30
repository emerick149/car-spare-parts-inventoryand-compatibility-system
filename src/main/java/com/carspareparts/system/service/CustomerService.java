package com.carspareparts.system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carspareparts.system.domain.Customer;
import com.carspareparts.system.repository.CustomerRepository;
import com.carspareparts.system.repository.StorekeeperRepository;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private StorekeeperRepository storekeeperRepository;

    // A1 (CRUD): create customer
    public String save(Customer entity) {
        customerRepository.save(entity);
        return "Customer saved successfully";
    }

    // A1 (CRUD): get all customers
    public List<Customer> getAll() { return customerRepository.findAll(); }

    // A1 (CRUD): get customer by id
    public Object getById(UUID id) {
        return customerRepository.findById(id)
                .map(customer -> (Object) customer)
                .orElse("The customer with that id does not exist");
    }

    // A1 (CRUD): update customer
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

    // B1: find customers by last name (case-insensitive, sorted A→Z by firstName)
    public List<Customer> getByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4: distinct customers who have placed orders with a specific storekeeper
    public Object getCustomersOfStorekeeper(UUID storekeeperID) {
        if (!storekeeperRepository.existsById(storekeeperID)) {
            return "The storekeeper with that id does not exist";
        }
        return customerRepository.findDistinctCustomersByStorekeeperId(storekeeperID);
    }

    // A1 (CRUD): delete customer
    public String delete(UUID id) {
        if (!customerRepository.existsById(id)) return "The customer with that id does not exist";
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}
