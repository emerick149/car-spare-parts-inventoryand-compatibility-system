package com.carspareparts.system.service;

import com.carspareparts.system.domain.VehicleMake;
import com.carspareparts.system.repository.VehicleMakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleMakeService {
    @Autowired private VehicleMakeRepository vehicleMakeRepository;

    public String save(VehicleMake entity) {
        vehicleMakeRepository.save(entity);
        return "Vehicle make saved successfully";
    }
    public List<VehicleMake> getAll() { return vehicleMakeRepository.findAll(); }

    public Object getById(UUID id) {
        return vehicleMakeRepository.findById(id).orElse("The vehicle make with that id does not exist");
    }

    public String update(UUID id, VehicleMake newData) {
        return vehicleMakeRepository.findById(id).map(existing -> {
            existing.setName(newData.getName());
            vehicleMakeRepository.save(existing);
            return "Vehicle make updated successfully";
        }).orElse("The vehicle make with that id does not exist");
    }

    public String delete(UUID id) {
        if (!vehicleMakeRepository.existsById(id)) return "The vehicle make with that id does not exist";
        vehicleMakeRepository.deleteById(id);
        return "Vehicle make deleted successfully";
    }
}