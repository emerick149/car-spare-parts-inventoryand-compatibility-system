package com.carspareparts.system.controller;

import com.carspareparts.system.domain.Storekeeper;
import com.carspareparts.system.service.StorekeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/storekeepers")
public class StorekeeperController {
    @Autowired private StorekeeperService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Storekeeper entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    @GetMapping("/by-vehicle-make")
    public ResponseEntity<?> getByVehicleMake(@RequestParam String name) {
        return new ResponseEntity<>(service.getByVehicleMake(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Storekeeper entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // D3: add a single vehicle make to a storekeeper
    @PutMapping("/{storekeeperID}/vehicle-makes/{vehicleMakeId}")
    public ResponseEntity<?> addVehicleMake(@PathVariable UUID storekeeperID, @PathVariable UUID vehicleMakeId) {
        Map.Entry<Integer, String> result = service.addVehicleMake(storekeeperID, vehicleMakeId);
        return new ResponseEntity<>(result.getValue(), HttpStatus.valueOf(result.getKey()));
    }

    // D4: move a storekeeper to a different store section by sectionNumber
    @PatchMapping("/{storekeeperID}/store-section")
    public ResponseEntity<?> changeStoreSection(@PathVariable UUID storekeeperID, @RequestParam int sectionNumber) {
        Map.Entry<Integer, String> result = service.changeStoreSection(storekeeperID, sectionNumber);
        return new ResponseEntity<>(result.getValue(), HttpStatus.valueOf(result.getKey()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}