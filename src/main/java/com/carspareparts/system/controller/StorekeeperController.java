package com.carspareparts.system.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carspareparts.system.domain.Storekeeper;
import com.carspareparts.system.service.StorekeeperService;

@RestController
@RequestMapping("/api/storekeepers")
public class StorekeeperController {
    @Autowired private StorekeeperService service;

    // A1 (CRUD): POST /api/storekeepers/save
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Storekeeper entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    // A1 (CRUD): GET /api/storekeepers/all
    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    // C1: GET /api/storekeepers/by-vehicle-make?name=...
    @GetMapping("/by-vehicle-make")
    public ResponseEntity<?> getByVehicleMake(@RequestParam String name) {
        return new ResponseEntity<>(service.getByVehicleMake(name), HttpStatus.OK);
    }

    // A1 (CRUD): GET /api/storekeepers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // A1 (CRUD): PUT /api/storekeepers/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Storekeeper entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // D3: PUT /api/storekeepers/{storekeeperID}/vehicle-makes/{vehicleMakeId}
    @PutMapping("/{storekeeperID}/vehicle-makes/{vehicleMakeId}")
    public ResponseEntity<?> addVehicleMake(@PathVariable UUID storekeeperID, @PathVariable UUID vehicleMakeId) {
        Map.Entry<Integer, String> result = service.addVehicleMake(storekeeperID, vehicleMakeId);
        return new ResponseEntity<>(result.getValue(), HttpStatus.valueOf(result.getKey()));
    }

    // D4: PATCH /api/storekeepers/{storekeeperID}/store-section?sectionNumber=...
    @PatchMapping("/{storekeeperID}/store-section")
    public ResponseEntity<?> changeStoreSection(@PathVariable UUID storekeeperID, @RequestParam int sectionNumber) {
        Map.Entry<Integer, String> result = service.changeStoreSection(storekeeperID, sectionNumber);
        return new ResponseEntity<>(result.getValue(), HttpStatus.valueOf(result.getKey()));
    }

    // A1 (CRUD): DELETE /api/storekeepers/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
