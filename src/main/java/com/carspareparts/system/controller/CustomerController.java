package com.carspareparts.system.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carspareparts.system.domain.Customer;
import com.carspareparts.system.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired private CustomerService service;

    // A1 (CRUD): POST /api/customers/save
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    // A1 (CRUD): GET /api/customers/all
    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    // B1: GET /api/customers/by-last-name?lastName=...
    @GetMapping("/by-last-name")
    public ResponseEntity<?> getByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(service.getByLastName(lastName), HttpStatus.OK);
    }

    // C4: GET /api/customers/of-storekeeper/{storekeeperID}
    @GetMapping("/of-storekeeper/{storekeeperID}")
    public ResponseEntity<?> getCustomersOfStorekeeper(@PathVariable UUID storekeeperID) {
        Object res = service.getCustomersOfStorekeeper(storekeeperID);
        return res instanceof String
                ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // A1 (CRUD): GET /api/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // A1 (CRUD): PUT /api/customers/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Customer entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // A1 (CRUD): DELETE /api/customers/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
