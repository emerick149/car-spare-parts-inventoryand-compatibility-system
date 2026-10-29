package com.carspareparts.system.controller;

import com.carspareparts.system.domain.Customer;
import com.carspareparts.system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired private CustomerService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    @GetMapping("/by-last-name")
    public ResponseEntity<?> getByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(service.getByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/of-storekeeper/{storekeeperID}")
    public ResponseEntity<?> getCustomersOfStorekeeper(@PathVariable UUID storekeeperID) {
        Object res = service.getCustomersOfStorekeeper(storekeeperID);
        return res instanceof String
                ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Customer entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}