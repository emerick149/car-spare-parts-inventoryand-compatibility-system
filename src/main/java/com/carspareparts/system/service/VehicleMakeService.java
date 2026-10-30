package com.carspareparts.system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carspareparts.system.domain.VehicleMake;
import com.carspareparts.system.repository.VehicleMakeRepository;

@Service
public class VehicleMakeService {
    @Autowired private VehicleMakeRepository vehicleMakeRepository;

    // A1 (CRUD): create vehicle make
    public String save(VehicleMake entity) {
        vehicleMakeRepository.save(entity);
        return "Vehicle make saved successfully";
    }

    // A1 (CRUD): get all vehicle makes
    public List<VehicleMake> getAll() { return vehicleMakeRepository.findAll(); }

    // A1 (CRUD): get vehicle make by id
    public Object getById(UUID id) {
        return vehicleMakeRepository.findById(id)
                .map(vehicleMake -> (Object) vehicleMake)
                .orElse("The vehicle make with that id does not exist");
    }

    // A1 (CRUD): update vehicle make
    public String update(UUID id, VehicleMake newData) {
        return vehicleMakeRepository.findById(id).map(existing -> {
            existing.setName(newData.getName());
            vehicleMakeRepository.save(existing);
            return "Vehicle make updated successfully";
        }).orElse("The vehicle make with that id does not exist");
    }

    // C3: get vehicle makes not held by any storekeeper
    public List<VehicleMake> getUnused() {
        return vehicleMakeRepository.findUnused();
    }

    // A1 (CRUD): delete vehicle make
    public String delete(UUID id) {
        if (!vehicleMakeRepository.existsById(id)) return "The vehicle make with that id does not exist";
        vehicleMakeRepository.deleteById(id);
        return "Vehicle make deleted successfully";
    }
}
