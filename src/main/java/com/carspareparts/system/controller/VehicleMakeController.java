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
import org.springframework.web.bind.annotation.RestController;

import com.carspareparts.system.domain.VehicleMake;
import com.carspareparts.system.service.VehicleMakeService;

@RestController
@RequestMapping("/api/vehicle-makes")
public class VehicleMakeController {
    @Autowired private VehicleMakeService service;

    // A1 (CRUD): POST /api/vehicle-makes/save
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody VehicleMake entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    // A1 (CRUD): GET /api/vehicle-makes/all
    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    // C3: GET /api/vehicle-makes/unused
    @GetMapping("/unused")
    public ResponseEntity<?> getUnused() {
        return new ResponseEntity<>(service.getUnused(), HttpStatus.OK);
    }

    // A1 (CRUD): GET /api/vehicle-makes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // A1 (CRUD): PUT /api/vehicle-makes/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody VehicleMake entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // A1 (CRUD): DELETE /api/vehicle-makes/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
